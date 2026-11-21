package com.security.service.impl;

import com.security.dto.LoginDto;
import com.security.dto.LoginResponseDto;
import com.security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtServiceImpl;
    private final UserServiceImpl userServiceImpl;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                        loginDto.getPassword()));
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtServiceImpl.generateAccessToken(user);
        String refreshToken = jwtServiceImpl.generateRefreshToken(user);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtServiceImpl.getUserIdFromToken(refreshToken);
        User user = userServiceImpl.getUserById(userId);
        String accessToken = jwtServiceImpl.generateAccessToken(user);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }
}
