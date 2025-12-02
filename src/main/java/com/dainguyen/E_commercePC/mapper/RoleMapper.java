package com.dainguyen.E_commercePC.mapper;

import org.mapstruct.Mapper;

import com.dainguyen.E_commercePC.dto.request.RoleRequest;
import com.dainguyen.E_commercePC.dto.response.RoleResponse;
import com.dainguyen.E_commercePC.entity.user.Role;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permission", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
