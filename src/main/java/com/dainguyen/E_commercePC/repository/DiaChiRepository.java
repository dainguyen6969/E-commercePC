package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.user.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {
    Set<DiaChi> findByUser_Id(Integer userId);
}
