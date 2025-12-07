package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.order.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {
}
