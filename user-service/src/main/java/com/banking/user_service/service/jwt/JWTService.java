package com.banking.user_service.service.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.banking.user_service.model.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;
    private static final String USERNAME = "USERNAME";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJWT(LocalUser user) {
        return JWT.create()
                .withClaim("userId", user.getId().toString())
                .withClaim(USERNAME, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token) {
        return JWT.decode(token)
                .getClaim(USERNAME)
                .asString();
    }

}
