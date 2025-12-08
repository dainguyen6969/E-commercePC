package com.dainguyen.E_commercePC.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dainguyen.E_commercePC.dto.request.UserCreationRequest;
import com.dainguyen.E_commercePC.dto.request.UserUpdateRequest;
import com.dainguyen.E_commercePC.dto.request.DiaChiRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.UserResponse;
import com.dainguyen.E_commercePC.dto.response.DiaChiResponse; // Import DTO Địa chỉ
import com.dainguyen.E_commercePC.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    final UserService userService;

    // POST /users: Đăng ký (Public)
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userCreationRequest))
                .build();
    }

    // GET /users: Lấy tất cả user (Chỉ ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    // *** ENDPOINT HỒ SƠ CÁ NHÂN ***

    // GET /users/my-profile
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/my-profile")
    public ApiResponse<UserResponse> getMyProfile() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyProfile())
                .build();
    }

    // PUT /users/my-profile
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/my-profile")
    public ApiResponse<UserResponse> updateMyProfile(@RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateMyProfile(request))
                .build();
    }

    // *** ENDPOINTS QUẢN LÝ ĐỊA CHỈ ***

    // GET /users/addresses: Lấy tất cả địa chỉ của User hiện tại
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/addresses")
    public ApiResponse<List<DiaChiResponse>> getMyAddresses() {
        return ApiResponse.<List<DiaChiResponse>>builder()
                .result(userService.getMyAddresses())
                .build();
    }

    // POST /users/addresses (Thêm địa chỉ cho User hiện tại)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/addresses")
    public ApiResponse<UserResponse> addAddress(@RequestBody DiaChiRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.addAddress(request))
                .build();
    }

    // DELETE /users/addresses/{diaChiId} (Xóa địa chỉ)
    @DeleteMapping("/addresses/{diaChiId}")
    public ApiResponse<UserResponse> deleteAddress(@PathVariable Integer diaChiId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.deleteAddress(diaChiId))
                .build();
    }

    // *** ENDPOINT ADMIN: Lấy tất cả địa chỉ trong hệ thống ***
    @GetMapping("/admin/addresses")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<DiaChiResponse>> getAllAddresses() {
        return ApiResponse.<List<DiaChiResponse>>builder()
                .result(userService.getAllAddresses())
                .build();
    }

    // *** ENDPOINT ADMIN: Lấy địa chỉ theo User ID ***
    @GetMapping("/admin/addresses/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<DiaChiResponse>> getAllAddressesByUser(@PathVariable Integer userId) {
        return ApiResponse.<List<DiaChiResponse>>builder()
                .result(userService.getAllAddressesByUserId(userId))
                .build();
    }
}