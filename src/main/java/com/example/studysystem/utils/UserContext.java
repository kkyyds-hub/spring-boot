package com.example.studysystem.utils;

public class UserContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_NAME = new ThreadLocal<>();

    public static void set(Long id, String name) {
        USER_ID.set(id);
        USER_NAME.set(name);
    }

    public static String get() {
        String name = USER_NAME.get();
        return name == null ? "系统" : name;
    }

    public static Long getUserId() {
        Long id = USER_ID.get();
        if (id == null) {
            throw new RuntimeException("未获取到登录用户");
        }
        return id;
    }

    public static void clear() {
        USER_ID.remove();
        USER_NAME.remove();
    }
}
