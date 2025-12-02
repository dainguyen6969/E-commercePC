package com.dainguyen.E_commercePC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dainguyen.E_commercePC.entity.user.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String name);
}
