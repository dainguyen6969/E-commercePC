package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.product.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
}
