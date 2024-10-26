package com.edsh.is_lab1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return hashPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedRawPassword = hashPassword(rawPassword.toString());
        return hashedRawPassword.equals(encodedPassword);
    }

    private String hashPassword(String password) {
        try {
            var digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return String.format("%032X", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MD5PasswordEncoder();
    }

}

