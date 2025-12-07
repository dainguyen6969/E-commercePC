package com.dainguyen.E_commercePC.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dainguyen.E_commercePC.dto.request.AuthenticationRequest;
import com.dainguyen.E_commercePC.dto.request.IntrospectRequest;
import com.dainguyen.E_commercePC.dto.response.AuthenticationResponse;
import com.dainguyen.E_commercePC.dto.response.IntrospectResponse;
import com.dainguyen.E_commercePC.entity.user.User;
import com.dainguyen.E_commercePC.exception.AppException;
import com.dainguyen.E_commercePC.exception.ErrorCode;
import com.dainguyen.E_commercePC.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Integer REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Integer VALIDATION_DURATION;


    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        boolean isValid = true;
        SignedJWT signedJWT = null;

        try {
            signedJWT = verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        // Lấy quyền (roles) trực tiếp từ claim "scope" trong JWT
        Set<String> roles = Collections.emptySet();
        if (isValid && signedJWT != null) {
            try {
                // Claim "scope" chứa tất cả Roles và Permissions, cần lọc ra chỉ Roles (có tiền tố ROLE_)
                String scopeClaim = signedJWT.getJWTClaimsSet().getStringClaim("scope");
                if (scopeClaim != null && !scopeClaim.isEmpty()) {
                    roles = Arrays.stream(scopeClaim.split(" "))
                            .filter(claim -> claim.startsWith("ROLE_")) // Lọc ra chỉ các Role
                            .collect(Collectors.toSet());
                }
            } catch (Exception e) {
                log.error("Error extracting scope claim from JWT during introspect.", e);
                isValid = false;
            }
        }

        // Trả về kết quả, bao gồm cả roles
        return IntrospectResponse.builder()
                .valid(isValid)
                .roles(roles) // Trả về vai trò đã trích xuất từ token
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository
                .findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).valid(true).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("Nguyen Trong Dai")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(VALIDATION_DURATION)))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException exception) {
            log.error("Cannot create token", exception);
            throw new RuntimeException(exception);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verifiedJWT = signedJWT.verify(jwsVerifier);

        if (!(verifiedJWT && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHORIZED);

        return signedJWT;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if(!CollectionUtils.isEmpty(role.getPermission())) {
                    role.getPermission().forEach(permission -> {
                        stringJoiner.add(permission.getPermissionName());
                    });
                }
            });
        }

        log.info(user.getRoles().toString());
        return stringJoiner.toString();
    }
}
