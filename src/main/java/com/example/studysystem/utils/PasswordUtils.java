package com.example.studysystem.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class PasswordUtils {
    public static String md5(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }
}
