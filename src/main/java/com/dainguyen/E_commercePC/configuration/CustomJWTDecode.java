package com.dainguyen.E_commercePC.configuration;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.dainguyen.E_commercePC.dto.request.IntrospectRequest;
import com.dainguyen.E_commercePC.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

@Component
public class CustomJWTDecode implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String jwtSignerKey;

    private NimbusJwtDecoder nimbus;

    @PostConstruct
    void init() {
        SecretKeySpec key = new SecretKeySpec(jwtSignerKey.getBytes(), "HmacSHA512");
        nimbus = NimbusJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return nimbus.decode(token);
    }
}
