package com.dainguyen.E_commercePC.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dainguyen.E_commercePC.dto.request.PermissionRequest;
import com.dainguyen.E_commercePC.dto.response.PermissionResponse;
import com.dainguyen.E_commercePC.entity.user.Permission;
import com.dainguyen.E_commercePC.mapper.PermissionMapper;
import com.dainguyen.E_commercePC.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    final PermissionRepository permissionRepository;
    final PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);

        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }
}
