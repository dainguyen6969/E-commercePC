package com.dainguyen.E_commercePC.entity.order;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String phuongThuc;
    Double soTien;
    String status;
    String moTa;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "don_hang_id", referencedColumnName = "id")
    DonHang donHang;
}
