package com.dainguyen.E_commercePC.mapper;

import org.mapstruct.Mapper;

import com.dainguyen.E_commercePC.dto.request.PermissionRequest;
import com.dainguyen.E_commercePC.dto.response.PermissionResponse;
import com.dainguyen.E_commercePC.entity.user.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
