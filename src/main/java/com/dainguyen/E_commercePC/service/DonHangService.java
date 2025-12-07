package com.dainguyen.E_commercePC.service;

import com.dainguyen.E_commercePC.dto.request.OrderCreationRequest;
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
import com.dainguyen.E_commercePC.repository.ChiTietDonHangRepository;
import com.dainguyen.E_commercePC.repository.ChiTietGioHangRepository;
import com.dainguyen.E_commercePC.repository.DonHangRepository;
import com.dainguyen.E_commercePC.repository.GioHangRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonHangService {
    DonHangRepository donHangRepository;
    ChiTietDonHangRepository chiTietDonHangRepository;
    GioHangRepository gioHangRepository;
    ChiTietGioHangRepository chiTietGioHangRepository;
    UserRepository userRepository;
    DonHangMapper donHangMapper;

    // Hàm giả định để lấy User ID từ Security Context
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
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)); // Giỏ hàng không tồn tại

        List<ChiTietGioHang> cartItems = chiTietGioHangRepository.findAllByGioHang_Id(gioHang.getId());

        if (cartItems.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_KEY); // Giỏ hàng trống
        }

        // 1. Tính toán tổng tiền
        double tongTien = cartItems.stream()
                .mapToDouble(item -> item.getGia() * item.getSoLuong())
                .sum();

        // 2. Tạo đối tượng DonHang
        DonHang donHang = DonHang.builder()
                .user(user)
                .tongTien(tongTien)
                .status("PENDING") // Trạng thái ban đầu
                .createdAt(LocalDateTime.now())
                // Các trường khác như địa chỉ nhận hàng có thể được thêm vào DonHang Entity
                .build();

        donHang = donHangRepository.save(donHang);

        // 3. Chuyển ChiTietGioHang thành ChiTietDonHang và trừ kho (Giả định)
        for (ChiTietGioHang cartItem : cartItems) {
            ChiTietDonHang chiTietDonHang = ChiTietDonHang.builder()
                    .donHang(donHang)
                    .sanPhamId(cartItem.getSanPham())
                    .soLuong(cartItem.getSoLuong())
                    .gia(cartItem.getGia())
                    .build();

            // Cần trừ số lượng tồn kho của SanPham ở đây (Logic Trừ kho cần được thêm vào)

            chiTietDonHangRepository.save(chiTietDonHang);
        }

        // 4. Xóa các mặt hàng trong giỏ hàng (Không xóa GioHang Entity)
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
}