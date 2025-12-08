package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.request.UserUpdateRequest;
import org.mapstruct.Mapper;

import com.dainguyen.E_commercePC.dto.request.UserCreationRequest;
import com.dainguyen.E_commercePC.dto.response.UserResponse;
import com.dainguyen.E_commercePC.entity.user.User;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "gioHang", ignore = true)
    @Mapping(target = "donHangs", ignore = true)
    @Mapping(target = "diaChi", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}