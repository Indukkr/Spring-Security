package com.security.service.impl;

import com.security.dto.SignUpDto;
import com.security.dto.UserDto;
import com.security.entities.User;
import com.security.exception.ResourceAlreadyExistException;
import com.security.exception.ResourceNotFoundException;
import com.security.repository.UserRepository;
import com.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                ()->new ResourceNotFoundException("User not found with email: " + username+" not found !!")
        );

    }

    @Override
    public UserDto signup(SignUpDto signUpDto) {
        userRepository.findByEmail(signUpDto.getEmail())
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistException(
                            "User with email: " + signUpDto.getEmail() + " already exists!"
                    );
                });
        User user = modelMapper.map(signUpDto, User.class);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }
}
