package dainguyen.E_commercePC.mapper;

import org.mapstruct.Mapper;

import dainguyen.E_commercePC.dto.request.PermissionRequest;
import dainguyen.E_commercePC.dto.response.PermissionResponse;
import dainguyen.E_commercePC.entity.user.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
