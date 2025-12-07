package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.request.DiaChiRequest;
import com.dainguyen.E_commercePC.dto.response.DiaChiResponse;
import com.dainguyen.E_commercePC.entity.user.DiaChi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiaChiMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    DiaChi toDiaChi(DiaChiRequest request);

    DiaChiResponse toDiaChiResponse(DiaChi diaChi);
}
