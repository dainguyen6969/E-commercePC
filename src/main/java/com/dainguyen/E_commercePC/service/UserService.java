package com.dainguyen.E_commercePC.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dainguyen.E_commercePC.constant.PredefinedRole;
import com.dainguyen.E_commercePC.dto.request.UserCreationRequest;
import com.dainguyen.E_commercePC.dto.response.UserResponse;
import com.dainguyen.E_commercePC.entity.user.Role;
import com.dainguyen.E_commercePC.entity.user.User;
import com.dainguyen.E_commercePC.exception.AppException;
import com.dainguyen.E_commercePC.exception.ErrorCode;
import com.dainguyen.E_commercePC.mapper.UserMapper;
import com.dainguyen.E_commercePC.repository.RoleRepository;
import com.dainguyen.E_commercePC.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    final UserRepository userRepository;
    final UserMapper userMapper;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findByRoleName(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRole(roles);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
}
