package com.dainguyen.E_commercePC.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.dainguyen.E_commercePC.dto.request.PermissionRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.PermissionResponse;
import com.dainguyen.E_commercePC.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    final PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(permissionRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermisison() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }
}
