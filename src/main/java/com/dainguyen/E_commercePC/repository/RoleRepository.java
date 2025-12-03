package com.dainguyen.E_commercePC.repository;

import java.util.Optional;

import com.dainguyen.E_commercePC.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String name);
}
