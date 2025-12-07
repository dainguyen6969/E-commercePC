package com.dainguyen.E_commercePC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGioHangResponse {
    Integer chiTietGioHangId; // ID của ChiTietGioHang

    // Thông tin sản phẩm (có thể dùng ProductResponse để gọn hơn, nhưng tách ra để đơn giản)
    Integer sanPhamId;
    String tenSanPham;
    String anhSanPham;

    Integer soLuong;
    Double giaHienTai;
    Double thanhTien; // soLuong * giaHienTai
}
