package com.dainguyen.E_commercePC.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGioHangRequest {
    Integer productId;
    Integer quantity; // Số lượng mới hoặc delta
}
