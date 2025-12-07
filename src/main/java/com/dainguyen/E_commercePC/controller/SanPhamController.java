package com.dainguyen.E_commercePC.controller;

import com.dainguyen.E_commercePC.dto.request.SanPhamRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.SanPhamResponse;
import com.dainguyen.E_commercePC.service.SanPhamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SanPhamController {
    final SanPhamService sanPhamService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<SanPhamResponse> createSanPham(@RequestBody SanPhamRequest sanPhamRequest) {
        return ApiResponse.<SanPhamResponse>builder()
                .result(sanPhamService.createSanPham(sanPhamRequest))
                .build();
    }


    @GetMapping
    public ApiResponse<List<SanPhamResponse>> getAllSanPham() {
        return ApiResponse.<List<SanPhamResponse>>builder()
                .result(sanPhamService.getAllSanPham())
                .build();
    }

    // *** ENDPOINT MỚI: Lấy chi tiết 1 sản phẩm theo ID ***
    @GetMapping("/{productId}")
    public ApiResponse<SanPhamResponse> getProductDetail(@PathVariable Integer productId) {
        return ApiResponse.<SanPhamResponse>builder()
                .result(sanPhamService.getProductById(productId))
                .build();
    }

    // *** ENDPOINT MỚI: Lấy sản phẩm liên quan theo Danh mục ***
    @GetMapping("/related/{danhMucId}/{excludedProductId}")
    public ApiResponse<List<SanPhamResponse>> getRelatedProducts(
            @PathVariable Integer danhMucId,
            @PathVariable Integer excludedProductId) {
        return ApiResponse.<List<SanPhamResponse>>builder()
                .result(sanPhamService.getRelatedProducts(danhMucId, excludedProductId))
                .build();
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteProduct(@PathVariable Integer productId) {
        sanPhamService.deleteProduct(productId);
        return ApiResponse.<Void>builder()
                .message("Xóa sản phẩm thành công")
                .build();
    }
}
