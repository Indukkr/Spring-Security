package com.security.controller;

import com.security.dto.LoginDto;
import com.security.dto.SignUpDto;
import com.security.dto.UserDto;
import com.security.service.UserService;
import com.security.service.impl.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthServiceImpl  authServiceImpl;

    @PostMapping("/signup")
    ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDto signUpDto){
        UserDto userDto=userService.signup(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    ResponseEntity<String>  loginUser(@RequestBody LoginDto loginDto,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        String token = authServiceImpl.login(loginDto);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

}
