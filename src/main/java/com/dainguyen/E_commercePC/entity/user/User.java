package com.dainguyen.E_commercePC.entity.user;

import java.time.LocalDateTime;
import java.util.Set;

import com.dainguyen.E_commercePC.entity.cart.GioHang;
import com.dainguyen.E_commercePC.entity.order.ChiTietDonHang;
import com.dainguyen.E_commercePC.entity.order.DonHang;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
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
    String phone;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    Set<DiaChi> diaChi;

    @ManyToMany
    Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    GioHang gioHang;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<DonHang> donHangs;
}
