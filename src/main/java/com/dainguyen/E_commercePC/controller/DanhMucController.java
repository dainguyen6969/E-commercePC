package com.dainguyen.E_commercePC.controller;

import com.dainguyen.E_commercePC.dto.request.DanhMucRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.DanhMucResponse;
import com.dainguyen.E_commercePC.service.DanhMucService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DanhMucController {
    final DanhMucService danhMucService;

    @PostMapping
    public ApiResponse<DanhMucResponse> createDanhMuc(@RequestBody DanhMucRequest danhMucRequest) {
        return ApiResponse.<DanhMucResponse>builder()
                .result(danhMucService.createDanhMuc(danhMucRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<DanhMucResponse>> getAllDanhMuc() {
        return ApiResponse.<List<DanhMucResponse>>builder()
                .result(danhMucService.getAllDanhMuc())
                .build();
    }

    @DeleteMapping("/{danhMucId}")
    public void deleteDanhMuc(@PathVariable Integer danhMucId) {
        danhMucService.deleteDanhMuc(danhMucId);
    }
}
