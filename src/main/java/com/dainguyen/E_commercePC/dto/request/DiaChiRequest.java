package com.dainguyen.E_commercePC.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChiRequest {
    String thanhPho;
    String xaPhuong;
    String diaChiHienTai;
    String phone;
}
