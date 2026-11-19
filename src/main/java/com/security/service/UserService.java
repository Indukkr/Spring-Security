package com.security.service;

import com.security.dto.SignUpDto;
import com.security.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto signup(SignUpDto signUpDto);
}
