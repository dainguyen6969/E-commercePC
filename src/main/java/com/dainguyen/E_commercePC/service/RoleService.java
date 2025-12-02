package com.dainguyen.E_commercePC.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dainguyen.E_commercePC.dto.request.RoleRequest;
import com.dainguyen.E_commercePC.dto.response.RoleResponse;
import com.dainguyen.E_commercePC.mapper.RoleMapper;
import com.dainguyen.E_commercePC.repository.PermissionRepository;
import com.dainguyen.E_commercePC.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    final RoleRepository roleRepository;
    final RoleMapper roleMapper;
    final PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);

        var permissions = permissionRepository.findAllById(roleRequest.getPermission());
        role.setPermission(new HashSet<>(permissions));

        roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }
}
