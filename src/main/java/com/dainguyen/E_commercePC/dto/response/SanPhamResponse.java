package com.dainguyen.E_commercePC.dto.response;

import com.dainguyen.E_commercePC.entity.product.DanhMuc;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamResponse {
    Integer id;

    String ten;
    Double gia;
    String anh;
    String moTa;
    Integer soLuong;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    DanhMuc danhMucId;
}
