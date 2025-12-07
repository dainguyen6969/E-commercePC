package com.dainguyen.E_commercePC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhMucResponse {
    Integer id;

    String ten;
    String moTa;
}
