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
    Double gia;

    @OneToOne
    @JoinColumn(name = "gio_hang_id", referencedColumnName = "id")
    GioHang gioHangId;

    @ManyToOne
    @JoinColumn(name = "san_pham_id", referencedColumnName = "id")
    SanPham sanPhamId;
}
