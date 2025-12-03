package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.response.UserResponse;
import com.dainguyen.E_commercePC.entity.user.User;
import org.mapstruct.Mapper;

import com.dainguyen.E_commercePC.dto.request.UserCreationRequest;


@Mapper(componentModel = "Spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);
}
