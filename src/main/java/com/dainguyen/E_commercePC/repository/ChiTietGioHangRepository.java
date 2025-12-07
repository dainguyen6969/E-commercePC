package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.cart.ChiTietGioHang;
import com.dainguyen.E_commercePC.entity.cart.GioHang;
import com.dainguyen.E_commercePC.entity.product.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, Integer> {
    Optional<ChiTietGioHang> findByGioHang_IdAndSanPham_Id(Integer gioHangId, Integer sanPhamId);

    List<ChiTietGioHang> findAllByGioHang_Id(Integer gioHangId);
}
