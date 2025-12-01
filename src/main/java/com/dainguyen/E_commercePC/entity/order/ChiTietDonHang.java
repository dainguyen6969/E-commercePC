package com.dainguyen.E_commercePC.entity.order;

import com.dainguyen.E_commercePC.entity.product.SanPham;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer soLuong;
    Double gia;

    @ManyToOne
    @JoinColumn(name = "don_hang_id", referencedColumnName = "id")
    DonHang donHang;

    @OneToOne
    @JoinColumn(name = "san_pham_id", referencedColumnName = "id")
    SanPham sanPhamId;
}
