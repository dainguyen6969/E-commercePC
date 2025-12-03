package dainguyen.E_commercePC.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dainguyen.E_commercePC.dto.request.RoleRequest;

import dainguyen.E_commercePC.dto.response.RoleResponse;
import dainguyen.E_commercePC.entity.user.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permission", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
