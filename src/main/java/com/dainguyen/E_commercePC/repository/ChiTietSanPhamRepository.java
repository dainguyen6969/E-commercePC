package com.dainguyen.E_commercePC.repository;

import com.dainguyen.E_commercePC.entity.product.ChiTietSanPham;
import com.dainguyen.E_commercePC.entity.product.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {

    @Modifying
    @Query("DELETE FROM ChiTietSanPham c WHERE c.sanPhamId.id = :sanPhamId")
    void deleteBySanPhamId(Integer sanPhamId);

    ChiTietSanPham findBySanPhamId(SanPham sanPhamId);

    ChiTietSanPham findBySanPhamId_Id(Integer sanPhamIdId);
}
