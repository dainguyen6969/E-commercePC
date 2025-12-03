package com.dainguyen.E_commercePC.configuration;

import java.util.HashSet;

import com.dainguyen.E_commercePC.constant.PredefinedRole;
import com.dainguyen.E_commercePC.entity.user.Role;
import com.dainguyen.E_commercePC.entity.user.User;
import com.dainguyen.E_commercePC.repository.RoleRepository;
import com.dainguyen.E_commercePC.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final String admin = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername(admin).isEmpty()) {
                roleRepository.save(Role.builder()
                        .roleName(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                Role roleAdmin = roleRepository.save(Role.builder()
                        .roleName(PredefinedRole.ADMIN_ROLE)
                        .description("ADMIN ROLE")
                        .build());

                var hashSetRole = new HashSet<Role>();
                hashSetRole.add(roleAdmin);

                User user = User.builder()
                        .username(admin)
                        .password(admin)
                        .role(hashSetRole)
                        .build();

                userRepository.save(user);
                log.info("Please change password User Admin!!!");
            }
            log.info("Successfully added User Admin");
        };
    }
}
