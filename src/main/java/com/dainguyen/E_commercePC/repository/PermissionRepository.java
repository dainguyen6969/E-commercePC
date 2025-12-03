package dainguyen.E_commercePC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dainguyen.E_commercePC.entity.user.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {}
