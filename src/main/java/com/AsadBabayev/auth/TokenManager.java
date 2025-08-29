package com.AsadBabayev.auth;

import com.AsadBabayev.entities.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {
    private final String SECRET = "mySuperSecretKey";

    public String generateToken(String username, Role role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role.name())
                .withIssuer("myApp")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .sign(Algorithm.HMAC256(SECRET));
    }
    public String validateAndGetUsername(String token) {
        DecodedJWT decoded = JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer("myApp")
                .build()
                .verify(token);
        return decoded.getSubject();
    }
    public Role getRole(String token){
        DecodedJWT decoded = JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer("myApp")
                .build()
                .verify(token);
        String role = decoded.getClaim("role").asString();
        return Role.valueOf(role);
    }
}