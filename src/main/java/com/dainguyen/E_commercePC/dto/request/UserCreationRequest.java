package com.dainguyen.E_commercePC.dto.request;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    Integer id;

    String username;
    String password;
    String email;
    String fullName;
    String phone;
    Boolean status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    Set<dainguyen.E_commercePC.entity.user.DiaChi> diaChi;
}
