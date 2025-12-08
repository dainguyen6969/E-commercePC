package com.dainguyen.E_commercePC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChiResponse {
    Integer id;
    String thanhPho;
    String xaPhuong;
    String diaChiHienTai;
    String phone;

}