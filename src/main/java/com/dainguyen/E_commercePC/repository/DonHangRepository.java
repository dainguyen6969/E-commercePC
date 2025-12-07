package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.order.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByUser_Id(Integer userId);
}
