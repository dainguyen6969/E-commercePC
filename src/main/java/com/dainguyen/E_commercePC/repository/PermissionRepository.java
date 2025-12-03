package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {}
