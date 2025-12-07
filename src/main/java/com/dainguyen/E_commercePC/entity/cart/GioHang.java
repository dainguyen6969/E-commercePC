package com.dainguyen.E_commercePC.entity.cart;

import java.time.LocalDateTime;
import java.util.Set;

import com.dainguyen.E_commercePC.entity.user.User;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    LocalDateTime createdAt;

    // *** THÊM: Liên kết 1-1 với User (side sở hữu FK) ***
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true) // Đảm bảo unique
    User user;

    // Thêm danh sách các mặt hàng trong giỏ
    @OneToMany(mappedBy = "gioHang", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ChiTietGioHang> chiTietGioHang;
}
