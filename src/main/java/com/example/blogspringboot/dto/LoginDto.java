package com.example.blogspringboot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginDto {
    @NotEmpty(message = "username not empty")
    private String usernameOrEmail;
    @NotEmpty
    @Size(min = 8, message ="description should have at least 10 characters")
    private String password;
}
