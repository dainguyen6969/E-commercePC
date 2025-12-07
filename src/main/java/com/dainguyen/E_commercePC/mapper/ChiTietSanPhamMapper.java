package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.request.ChiTietSanPhamRequest;
import com.dainguyen.E_commercePC.dto.response.ChiTietSanPhamResponse;
import com.dainguyen.E_commercePC.entity.product.ChiTietSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietSanPhamMapper {
    @Mapping(target = "sanPhamId", ignore = true)
    ChiTietSanPham toChiTietSanPham(ChiTietSanPhamRequest chiTietSanPhamRequest);

    ChiTietSanPhamResponse toChiTietSanPhamResponse(ChiTietSanPham chiTietSanPham);
}
