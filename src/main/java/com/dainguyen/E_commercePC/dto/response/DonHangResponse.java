package com.dainguyen.E_commercePC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonHangResponse {
    Integer id;

    Double tongTien;
    String status;

    LocalDateTime createdAt;
}
