package com.dainguyen.E_commercePC.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhMucRequest {
    Integer id;

    String ten;
    String moTa;
}
