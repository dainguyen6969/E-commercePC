package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.request.DanhMucRequest;
import com.dainguyen.E_commercePC.dto.response.DanhMucResponse;
import com.dainguyen.E_commercePC.entity.product.DanhMuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DanhMucMapper {
    DanhMuc toDanhMuc(DanhMucRequest danhMucRequest);

    DanhMucResponse toDanhMucResponse(DanhMuc danhMuc);
}
