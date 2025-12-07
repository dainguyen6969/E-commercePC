package com.dainguyen.E_commercePC.controller;

import com.dainguyen.E_commercePC.dto.request.ChiTietSanPhamRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.ChiTietSanPhamResponse;
import com.dainguyen.E_commercePC.service.ChiTietSanPhamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detail-product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChiTietSanPhamController {
    final ChiTietSanPhamService chiTietSanPhamService;

    @PostMapping
    public ApiResponse<ChiTietSanPhamResponse> createChiTietSanPham(@RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest) {
        return ApiResponse.<ChiTietSanPhamResponse>builder()
                .result(chiTietSanPhamService.createChiTietSanPham(chiTietSanPhamRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ChiTietSanPhamResponse>> getAllChiTietSanPham() {
        return ApiResponse.<List<ChiTietSanPhamResponse>>builder()
                .result(chiTietSanPhamService.getAllChiTietSanPham())
                .build();
    }
}
