package com.dainguyen.E_commercePC.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.dainguyen.E_commercePC.entity.user.DiaChi;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;

    String username;
    String password;
    String email;
    String fullName;
    String phone;
    Boolean status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    Set<DiaChi> diaChi;
}
