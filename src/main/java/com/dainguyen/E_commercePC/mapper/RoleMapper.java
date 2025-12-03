package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.response.RoleResponse;
import com.dainguyen.E_commercePC.entity.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dainguyen.E_commercePC.dto.request.RoleRequest;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permission", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
