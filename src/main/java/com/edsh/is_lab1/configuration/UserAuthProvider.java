package com.edsh.is_lab1.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    private final UserService userService;
    @Value("${jwt.secretKey}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60);
        return JWT.create()
                .withIssuer(login)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        var verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        var decoded = verifier.verify(token);
        User user = userService.findByLogin(decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(user, null,
                List.of(() -> user.getPermission().toString()));
    }

}
