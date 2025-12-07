package com.dainguyen.E_commercePC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GioHangResponse {
    Integer gioHangId;
    List<ChiTietGioHangResponse> items;
    Double tongTienGioHang;
    Integer tongSoLuong;
}
