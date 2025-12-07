package com.dainguyen.E_commercePC.dto.response;

import com.dainguyen.E_commercePC.entity.product.SanPham;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietSanPhamResponse {
    Integer id;

    String moTa;
    String thuocTinh;
    String giaTri;
    Integer soLuong;

    SanPham sanPhamId;
}
