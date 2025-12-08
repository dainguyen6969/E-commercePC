package com.dainguyen.E_commercePC.controller;

import com.dainguyen.E_commercePC.dto.request.OrderCreationRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.OrderDetailResponse;
import com.dainguyen.E_commercePC.dto.response.OrderResponse;
import com.dainguyen.E_commercePC.service.DonHangService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    final DonHangService donHangService;

    // Tạo đơn hàng từ giỏ hàng (Yêu cầu User đăng nhập)

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(donHangService.createOrderFromCart(request))
                .build();
    }

    // Xem lịch sử đơn hàng của User hiện tại (Yêu cầu User đăng nhập)
    @GetMapping
    public ApiResponse<List<OrderResponse>> getOrderHistory() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(donHangService.getUserOrders())
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponse> getOrderDetail(@PathVariable Integer orderId) {
        return ApiResponse.<OrderDetailResponse>builder()
                .result(donHangService.getOrderDetail(orderId))
                .build();
    }
}
