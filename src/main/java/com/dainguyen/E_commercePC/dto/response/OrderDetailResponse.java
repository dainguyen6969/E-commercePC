package com.dainguyen.E_commercePC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Integer id;
    Double tongTien;
    String status;

    // Thông tin địa chỉ và thanh toán (từ DonHang Entity)
    String diaChiNhanHang;
    String phuongThucThanhToan;

    LocalDateTime createdAt;

    // Danh sách các mặt hàng (sử dụng OrderItemResponse)
    List<OrderItemResponse> items;
}
