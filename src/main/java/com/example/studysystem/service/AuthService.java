package com.example.studysystem.service;

import com.example.studysystem.dto.LoginDTO;
import com.example.studysystem.dto.RegisterDTO;
import com.example.studysystem.vo.LoginVO;

public interface AuthService {
    LoginVO login(LoginDTO dto);

    Long register(RegisterDTO dto);
}
