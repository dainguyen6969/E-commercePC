package com.dainguyen.E_commercePC.repository;

import java.util.Optional;

import com.dainguyen.E_commercePC.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
