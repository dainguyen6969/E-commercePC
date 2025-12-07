package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.cart.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    Optional<GioHang> findByUserId(Integer userId);
}
