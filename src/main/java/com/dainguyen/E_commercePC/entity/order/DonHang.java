package com.dainguyen.E_commercePC.entity.order;

import java.time.LocalDateTime;
import java.util.Set;

import com.dainguyen.E_commercePC.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Double tongTien;
    String status;

    String diaChiNhanHang;
    String phuongThucThanhToan;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    User user;

    // Thêm danh sách chi tiết đơn hàng
    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<ChiTietDonHang> chiTietDonHang;
}
