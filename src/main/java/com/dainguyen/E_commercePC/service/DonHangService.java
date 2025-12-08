package com.dainguyen.E_commercePC.service;

import com.dainguyen.E_commercePC.dto.request.OrderCreationRequest;
import com.dainguyen.E_commercePC.dto.response.OrderDetailResponse;
import com.dainguyen.E_commercePC.dto.response.OrderResponse;
import com.dainguyen.E_commercePC.entity.cart.ChiTietGioHang;
import com.dainguyen.E_commercePC.entity.cart.GioHang;
import com.dainguyen.E_commercePC.entity.order.ChiTietDonHang;
import com.dainguyen.E_commercePC.entity.order.DonHang;
import com.dainguyen.E_commercePC.entity.user.User;
import com.dainguyen.E_commercePC.exception.AppException;
import com.dainguyen.E_commercePC.exception.ErrorCode;
import com.dainguyen.E_commercePC.mapper.DonHangMapper;
import com.dainguyen.E_commercePC.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonHangService {
    final DonHangRepository donHangRepository;
    final ChiTietDonHangRepository chiTietDonHangRepository;
    final GioHangRepository gioHangRepository;
    final ChiTietGioHangRepository chiTietGioHangRepository;
    final UserRepository userRepository;
    final DonHangMapper donHangMapper;

    private Integer getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }

    @Transactional
    public OrderResponse createOrderFromCart(OrderCreationRequest request) {
        Integer userId = getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        GioHang gioHang = gioHangRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        List<ChiTietGioHang> cartItems = chiTietGioHangRepository.findAllByGioHang_Id(gioHang.getId());

        if (cartItems.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        double tongTien = cartItems.stream()
                .mapToDouble(item -> item.getGia() * item.getSoLuong())
                .sum();

        DonHang donHang = DonHang.builder()
                .user(user)
                .tongTien(tongTien)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .diaChiNhanHang(request.getDiaChiNhanHang())
                .phuongThucThanhToan(request.getPhuongThucThanhToan())
                .build();

        donHang = donHangRepository.save(donHang);

        for (ChiTietGioHang cartItem : cartItems) {
            ChiTietDonHang chiTietDonHang = ChiTietDonHang.builder()
                    .donHang(donHang)
                    .sanPham(cartItem.getSanPham())
                    .soLuong(cartItem.getSoLuong())
                    .gia(cartItem.getGia())
                    .build();

            // Logic Trừ kho (Cần thêm)

            chiTietDonHangRepository.save(chiTietDonHang);
        }

        chiTietGioHangRepository.deleteAll(cartItems);

        return donHangMapper.toOrderResponse(donHang);
    }

    public List<OrderResponse> getUserOrders() {
        Integer userId = getCurrentUserId();
        List<DonHang> donHangs = donHangRepository.findByUser_Id(userId);

        return donHangs.stream()
                .map(donHangMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDetailResponse getOrderDetail(Integer orderId) {
        Integer userId = getCurrentUserId();

        // 1. Tìm đơn hàng
        DonHang donHang = donHangRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        // 2. Kiểm tra quyền sở hữu
        if (donHang.getUser() == null || !donHang.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // 3. Ánh xạ sang OrderDetailResponse. MapStruct sẽ tự động dùng toOrderItemResponse
        // để xử lý danh sách item lồng bên trong.
        return donHangMapper.toOrderDetailResponse(donHang);
    }
}