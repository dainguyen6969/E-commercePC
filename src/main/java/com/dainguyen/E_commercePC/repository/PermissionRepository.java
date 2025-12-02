package com.dainguyen.E_commercePC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dainguyen.E_commercePC.entity.user.Permission;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
