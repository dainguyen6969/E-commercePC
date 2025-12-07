package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.response.ChiTietGioHangResponse;
import com.dainguyen.E_commercePC.entity.cart.ChiTietGioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GioHangMapper {

    @Mapping(target = "chiTietGioHangId", source = "id")

    // Lấy ID sản phẩm
    @Mapping(target = "sanPhamId", source = "sanPham.id")

    // Tên + ảnh sản phẩm
    @Mapping(target = "tenSanPham", source = "sanPham.ten")
    @Mapping(target = "anhSanPham", source = "sanPham.anh")

    // Giá hiện tại – fallback về 0 nếu null
    @Mapping(target = "giaHienTai",
            expression = "java(chiTietGioHang.getGia() != null ? chiTietGioHang.getGia() : 0.0)")

    @Mapping(target = "soLuong", source = "soLuong")

    // Thanh tiền – tính an toàn
    @Mapping(target = "thanhTien",
            expression = "java((chiTietGioHang.getGia() != null && chiTietGioHang.getSoLuong() != null) "
                    + "? chiTietGioHang.getGia() * chiTietGioHang.getSoLuong() : 0.0)")
    ChiTietGioHangResponse toChiTietGioHangResponse(ChiTietGioHang chiTietGioHang);
}
