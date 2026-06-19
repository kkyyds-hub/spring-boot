package com.example.studysystem.interceptor;

import com.example.studysystem.utils.JwtUtils;
import com.example.studysystem.utils.UserContext;
import com.example.studysystem.vo.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    public TokenInterceptor(JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("token");
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(token) && StringUtils.hasText(authorization)) {
            token = authorization.replace("Bearer ", "");
        }
        if (!StringUtils.hasText(token)) {
            writeError(response);
            return false;
        }
        try {
            Claims claims = jwtUtils.parseToken(token);
            Number id = (Number) claims.get("id");
            UserContext.set(id.longValue(), String.valueOf(claims.get("name")));
            return true;
        } catch (Exception e) {
            writeError(response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }

    private void writeError(HttpServletResponse response) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResult.error("未登录或登录已过期")));
    }
}
