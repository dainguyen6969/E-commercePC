package com.dainguyen.E_commercePC.controller;

import com.dainguyen.E_commercePC.dto.request.ChiTietGioHangRequest;
import com.dainguyen.E_commercePC.dto.request.ChiTietSanPhamRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.GioHangResponse;
import com.dainguyen.E_commercePC.service.GioHangService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GioHangController {
    final GioHangService gioHangService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/items")
    public ApiResponse<GioHangResponse> addOrUpdateCartItem(@RequestBody ChiTietGioHangRequest request) {
        return ApiResponse.<GioHangResponse>builder()
                .result(gioHangService.addOrUpdateProduct(request))
                .build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ApiResponse<GioHangResponse> getCart() {
        return ApiResponse.<GioHangResponse>builder()
                .result(gioHangService.getCartDetails())
                .build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/items/{itemId}")
    public ApiResponse<Void> removeCartItem(@PathVariable Integer itemId) {
        gioHangService.removeProduct(itemId);
        return ApiResponse.<Void>builder()
                .message("Xóa mặt hàng khỏi giỏ hàng thành công")
                .build();
    }
}
