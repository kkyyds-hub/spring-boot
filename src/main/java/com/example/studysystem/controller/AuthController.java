package com.example.studysystem.controller;

import com.example.studysystem.dto.LoginDTO;
import com.example.studysystem.dto.RegisterDTO;
import com.example.studysystem.service.AuthService;
import com.example.studysystem.vo.ApiResult;
import com.example.studysystem.vo.LoginVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 登录接口
    @PostMapping("/login")
    public ApiResult<LoginVO> login(@RequestBody LoginDTO dto) {
        try {
            return ApiResult.success(authService.login(dto));
        } catch (RuntimeException e) {
            return ApiResult.error(e.getMessage());
        }
    }

    // 注册接口
    @PostMapping("/register")
    public ApiResult<Long> register(@RequestBody RegisterDTO dto) {
        try {
            return ApiResult.success(authService.register(dto));
        } catch (RuntimeException e) {
            return ApiResult.error(e.getMessage());
        }
    }
}
