package com.dainguyen.E_commercePC.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.dainguyen.E_commercePC.dto.request.ChiTietGioHangRequest;
import com.dainguyen.E_commercePC.dto.response.ChiTietGioHangResponse;
import com.dainguyen.E_commercePC.repository.ChiTietGioHangRepository;
import com.dainguyen.E_commercePC.repository.GioHangRepository;
import com.dainguyen.E_commercePC.repository.SanPhamRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dainguyen.E_commercePC.dto.response.GioHangResponse;
import com.dainguyen.E_commercePC.entity.cart.ChiTietGioHang;
import com.dainguyen.E_commercePC.entity.cart.GioHang;
import com.dainguyen.E_commercePC.entity.product.SanPham;
import com.dainguyen.E_commercePC.entity.user.User;
import com.dainguyen.E_commercePC.exception.AppException;
import com.dainguyen.E_commercePC.exception.ErrorCode;
import com.dainguyen.E_commercePC.mapper.GioHangMapper;
import com.dainguyen.E_commercePC.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GioHangService {

    GioHangRepository gioHangRepository;
    ChiTietGioHangRepository chiTietGioHangRepository;
    SanPhamRepository sanPhamRepository;
    UserRepository userRepository;
    GioHangMapper gioHangMapper;

    // =============================
    // Lấy User đang đăng nhập
    // =============================
    private User getCurrentUserEntity() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }

    // =============================
    // Lấy hoặc tạo giỏ hàng
    // =============================
    private GioHang getOrCreateCart(User user) {
        return gioHangRepository.findByUserId(user.getId())
                .orElseGet(() -> gioHangRepository.save(
                        GioHang.builder()
                                .user(user)
                                .createdAt(LocalDateTime.now())
                                .build()
                ));
    }

    // =============================
    // Add hoặc Update sản phẩm
    // =============================
    @Transactional
    public GioHangResponse addOrUpdateProduct(ChiTietGioHangRequest request) {

        User user = getCurrentUserEntity();
        GioHang gioHang = getOrCreateCart(user);

        SanPham sanPham = sanPhamRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        // Kiểm tra tồn kho
        if (sanPham.getSoLuong() == null || sanPham.getSoLuong() < request.getQuantity()) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        Optional<ChiTietGioHang> existingItem =
                chiTietGioHangRepository.findByGioHang_IdAndSanPham_Id(
                        gioHang.getId(),
                        sanPham.getId()
                );

        ChiTietGioHang item;

        if (existingItem.isPresent()) {
            item = existingItem.get();
            item.setSoLuong(request.getQuantity());
        } else {
            item = ChiTietGioHang.builder()
                    .gioHang(gioHang)
                    .sanPham(sanPham)
                    .soLuong(request.getQuantity())
                    .gia(sanPham.getGia() != null ? sanPham.getGia() : 0.0)
                    .build();
        }

        // Không cho null
        item.setGia(sanPham.getGia() != null ? sanPham.getGia() : 0.0);

        chiTietGioHangRepository.save(item);

        return getCartDetails();
    }

    // =============================
    // Xóa sản phẩm khỏi giỏ hàng
    // =============================
    @Transactional
    public void removeProduct(Integer chiTietGioHangId) {
        if (!chiTietGioHangRepository.existsById(chiTietGioHangId)) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        // TODO: Kiểm tra quyền sở hữu (authorization)

        chiTietGioHangRepository.deleteById(chiTietGioHangId);
    }

    // =============================
    // Lấy chi tiết giỏ hàng
    // =============================
    public GioHangResponse getCartDetails() {
        User user = getCurrentUserEntity();
        GioHang gioHang = getOrCreateCart(user);

        List<ChiTietGioHang> items =
                chiTietGioHangRepository.findAllByGioHang_Id(gioHang.getId());

        List<ChiTietGioHangResponse> itemResponses = items.stream()
                .map(gioHangMapper::toChiTietGioHangResponse)
                .collect(Collectors.toList());

        double tongTien = itemResponses.stream()
                .mapToDouble(item -> item.getThanhTien() != null ? item.getThanhTien() : 0.0)
                .sum();

        int tongSoLuong = itemResponses.stream()
                .mapToInt(item -> item.getSoLuong() != null ? item.getSoLuong() : 0)
                .sum();

        return GioHangResponse.builder()
                .gioHangId(gioHang.getId())
                .items(itemResponses)
                .tongTienGioHang(tongTien)
                .tongSoLuong(tongSoLuong)
                .build();
    }
}
