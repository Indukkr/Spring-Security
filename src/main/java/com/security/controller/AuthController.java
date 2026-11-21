package com.security.controller;

import com.security.dto.LoginDto;
import com.security.dto.LoginResponseDto;
import com.security.dto.SignUpDto;
import com.security.dto.UserDto;
import com.security.service.UserService;
import com.security.service.impl.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path="/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthServiceImpl  authServiceImpl;

    @Value("${deploy.env}")
    private final String deployEnv;

    @PostMapping("/signup")
    ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDto signUpDto){
        UserDto userDto=userService.signup(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDto>  loginUser(@RequestBody LoginDto loginDto,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        LoginResponseDto loginResponseDto = authServiceImpl.login(loginDto);
        Cookie cookie = new Cookie("accessToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    ResponseEntity<LoginResponseDto>  refreshUser(HttpServletRequest request){
       String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Referesh Token Not Found inside the Cookie")
                );
       LoginResponseDto loginResponseDto = authServiceImpl.refreshToken(refreshToken);
       return ResponseEntity.ok(loginResponseDto);
    }


}
