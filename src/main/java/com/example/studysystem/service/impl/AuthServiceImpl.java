package com.example.studysystem.service.impl;

import com.example.studysystem.dto.LoginDTO;
import com.example.studysystem.dto.RegisterDTO;
import com.example.studysystem.entity.User;
import com.example.studysystem.mapper.UserMapper;
import com.example.studysystem.service.AuthService;
import com.example.studysystem.utils.JwtUtils;
import com.example.studysystem.utils.PasswordUtils;
import com.example.studysystem.vo.LoginVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserMapper userMapper, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginVO login(LoginDTO dto) {
        if (dto == null || !StringUtils.hasText(dto.getUsername()) || !StringUtils.hasText(dto.getPassword())) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        User user = userMapper.findByUsername(dto.getUsername());
        if (user == null || !PasswordUtils.md5(dto.getPassword()).equals(user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        LoginVO vo = new LoginVO(user.getId(), user.getUsername(), user.getName(), null);
        vo.setToken(jwtUtils.createToken(vo));
        return vo;
    }

    @Override
    public Long register(RegisterDTO dto) {
        if (dto == null || !StringUtils.hasText(dto.getUsername()) || !StringUtils.hasText(dto.getPassword())) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        if (userMapper.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordUtils.md5(dto.getPassword()));
        user.setName(StringUtils.hasText(dto.getName()) ? dto.getName() : dto.getUsername());
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
        return user.getId();
    }
}
