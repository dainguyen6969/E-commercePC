package com.dainguyen.E_commercePC.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonHangRequest {
    Integer id;

    Double tongTien;
    String status;

    LocalDateTime createdAt;
}
