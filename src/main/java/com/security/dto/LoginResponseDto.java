package com.security.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private Long id;
    private String accessToken;
    private String refreshToken;
}
