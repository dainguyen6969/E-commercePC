package com.dainguyen.E_commercePC.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dainguyen.E_commercePC.constant.PredefinedRole;
import com.dainguyen.E_commercePC.dto.request.UserCreationRequest;
import com.dainguyen.E_commercePC.dto.request.UserUpdateRequest;
import com.dainguyen.E_commercePC.dto.request.DiaChiRequest;
import com.dainguyen.E_commercePC.dto.response.UserResponse;
import com.dainguyen.E_commercePC.dto.response.DiaChiResponse; // Import DTO Địa chỉ
import com.dainguyen.E_commercePC.entity.user.Role;
import com.dainguyen.E_commercePC.entity.user.User;
import com.dainguyen.E_commercePC.entity.user.DiaChi;
import com.dainguyen.E_commercePC.exception.AppException;
import com.dainguyen.E_commercePC.exception.ErrorCode;
import com.dainguyen.E_commercePC.mapper.UserMapper;
import com.dainguyen.E_commercePC.mapper.DiaChiMapper;
import com.dainguyen.E_commercePC.repository.RoleRepository;
import com.dainguyen.E_commercePC.repository.UserRepository;
import com.dainguyen.E_commercePC.repository.DiaChiRepository;

import jakarta.transaction.Transactional;
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
    final DiaChiRepository diaChiRepository;
    final DiaChiMapper diaChiMapper;

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Sử dụng findByUsernameWithDetails (giả định có FETCH JOIN DiaChi và Roles)
    private User getCurrentUserEntity() {
        String username = getCurrentUsername();
        // SỬ DỤNG PHƯƠNG THỨC FETCH JOIN ĐẦY ĐỦ
        return userRepository.findByUsernameWithDetails(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }


    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findByRoleName(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        LocalDateTime dateTime = LocalDateTime.now();

        user.setCreatedAt(dateTime);
        user.setRoles(roles);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    // Lấy thông tin người dùng hiện tại
    public UserResponse getMyProfile() {
        User user = getCurrentUserEntity();
        return userMapper.toUserResponse(user);
    }

    // Cập nhật thông tin người dùng hiện tại
    @Transactional
    public UserResponse updateMyProfile(UserUpdateRequest request) {
        String username = getCurrentUsername();

        // Lấy Entity đang được Managed
        User managedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        // CHẮC CHẮN RẰNG MAPPER CÓ TRƯỜNG PHONE SAU KHI SỬA User.java
        userMapper.updateUser(managedUser, request);
        managedUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(managedUser);

        // Tải lại Entity với chi tiết đầy đủ để trả về Response
        User updatedUser = userRepository.findByUsernameWithDetails(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(updatedUser);
    }

    // Thêm địa chỉ mới
    @Transactional
    public UserResponse addAddress(DiaChiRequest request) {
        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        DiaChi diaChi = diaChiMapper.toDiaChi(request);
        diaChi.setUser(user);
        diaChi.setPhone(request.getPhone());

        diaChiRepository.save(diaChi);

        // Tải lại User với chi tiết đầy đủ
        User updatedUser = userRepository.findByUsernameWithDetails(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(updatedUser);
    }

    // Xóa địa chỉ theo ID
    @Transactional
    public UserResponse deleteAddress(Integer diaChiId) {
        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        DiaChi diaChi = diaChiRepository.findById(diaChiId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (!diaChi.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        diaChiRepository.delete(diaChi);

        // Tải lại User với chi tiết đầy đủ
        User updatedUser = userRepository.findByUsernameWithDetails(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(updatedUser);
    }

    // Lấy địa chỉ của User hiện tại
    @Transactional
    public List<DiaChiResponse> getMyAddresses() {
        User user = getCurrentUserEntity();

        List<DiaChi> diaChis = diaChiRepository.findByUser_Id(user.getId());
        diaChis.forEach(d -> System.out.println(">>> PHONE DB = " + d.getPhone()));
        return diaChis.stream()
                .map(diaChiMapper::toDiaChiResponse)
                .collect(Collectors.toList());
    }

    // Lấy tất cả địa chỉ của một User cụ thể (cho Admin)
    public List<DiaChiResponse> getAllAddressesByUserId(Integer userId) {
        List<DiaChi> diaChis = diaChiRepository.findByUser_Id(userId);

        return diaChis.stream()
                .map(diaChiMapper::toDiaChiResponse)
                .collect(Collectors.toList());
    }

    // Lấy tất cả địa chỉ trong hệ thống (Chỉ Admin)
    public List<DiaChiResponse> getAllAddresses() {
        return diaChiRepository.findAll().stream()
                .map(diaChiMapper::toDiaChiResponse)
                .collect(Collectors.toList());
    }
}