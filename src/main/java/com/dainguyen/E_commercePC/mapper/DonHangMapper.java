package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.response.OrderDetailResponse;
import com.dainguyen.E_commercePC.dto.response.OrderItemResponse;
import com.dainguyen.E_commercePC.dto.response.OrderResponse;
import com.dainguyen.E_commercePC.entity.order.ChiTietDonHang;
import com.dainguyen.E_commercePC.entity.order.DonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    // Ánh xạ tổng quan cho lịch sử đơn hàng
    OrderResponse toOrderResponse(DonHang donHang);

    // [BƯỚC 1] Ánh xạ chi tiết đơn hàng (DonHang Entity -> OrderDetailResponse DTO)
    @Mapping(target = "items", source = "chiTietDonHang")
    @Mapping(target = "diaChiNhanHang", source = "diaChiNhanHang") // Ánh xạ trường địa chỉ
    @Mapping(target = "phuongThucThanhToan", source = "phuongThucThanhToan") // Ánh xạ phương thức TT
    OrderDetailResponse toOrderDetailResponse(DonHang donHang);

    // [BƯỚC 2] Phương thức ánh xạ chi tiết từng mặt hàng (ChiTietDonHang Entity -> OrderItemResponse DTO)
    // MapStruct tự động sử dụng hàm này cho mỗi phần tử trong Set<ChiTietDonHang>
    @Mapping(target = "chiTietDonHangId", source = "id")
    @Mapping(target = "sanPhamId", source = "sanPham.id")
    @Mapping(target = "tenSanPham", source = "sanPham.ten") // Lấy tên sản phẩm (từ SanPham EAGER)
    @Mapping(target = "anhSanPham", source = "sanPham.anh") // Lấy ảnh sản phẩm (từ SanPham EAGER)
    @Mapping(target = "giaMua", source = "gia")
    @Mapping(target = "thanhTien", expression = "java(chiTietDonHang.getGia() * chiTietDonHang.getSoLuong())")
    OrderItemResponse toOrderItemResponse(ChiTietDonHang chiTietDonHang);
}
