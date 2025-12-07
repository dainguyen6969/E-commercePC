package com.dainguyen.E_commercePC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO đại diện cho lịch sử đơn hàng.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer id;
    Double tongTien;
    String status;
    LocalDateTime createdAt;

    // Chi tiết địa chỉ/người nhận nếu cần
    // List<OrderItemResponse> items; // Có thể có DTO cho chi tiết item
}