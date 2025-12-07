package com.dainguyen.E_commercePC.entity.cart;

import jakarta.persistence.*;

import com.dainguyen.E_commercePC.entity.product.SanPham;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietGioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer soLuong;

    // giá tại thời điểm thêm giỏ
    Double gia;

    @ManyToOne
    @JoinColumn(name = "gio_hang", nullable = false)
    GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "san_pham_id", nullable = false)
    SanPham sanPham;
}
