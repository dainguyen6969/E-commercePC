package com.dainguyen.E_commercePC.entity.user;

import java.time.LocalDateTime;
import java.util.Set;

import com.dainguyen.E_commercePC.entity.cart.GioHang;
import com.dainguyen.E_commercePC.entity.order.ChiTietDonHang;
import com.dainguyen.E_commercePC.entity.order.DonHang;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String username;
    String password;
    String email;
    String fullName;
    Boolean status;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany
    Set<DiaChi> diaChi;

    @ManyToMany
    Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    GioHang gioHang;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<DonHang> donHangs;
}
