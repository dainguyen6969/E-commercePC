package dainguyen.E_commercePC.mapper;

import org.mapstruct.Mapper;

import com.dainguyen.E_commercePC.dto.request.UserCreationRequest;

import dainguyen.E_commercePC.dto.response.UserResponse;
import dainguyen.E_commercePC.entity.user.User;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);
}
