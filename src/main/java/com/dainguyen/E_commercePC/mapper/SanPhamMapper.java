package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.request.SanPhamRequest;
import com.dainguyen.E_commercePC.dto.response.SanPhamResponse;
import com.dainguyen.E_commercePC.entity.product.SanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SanPhamMapper {
    @Mapping(target = "danhMucId", ignore = true)
    SanPham toSanPham(SanPhamRequest sanPhamRequest);

    SanPhamResponse toSanPhamResponse(SanPham sanPham);
}
