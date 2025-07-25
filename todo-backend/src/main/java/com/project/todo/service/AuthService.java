package com.project.todo.service;

import com.project.todo.dto.JwtAuthResponse;
import com.project.todo.dto.LoginDto;
import com.project.todo.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
