package com.example.blogspringboot.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor

public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public JWTAuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public JWTAuthResponse() {
    }
}