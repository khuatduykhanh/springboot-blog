package com.example.blogspringboot.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninDto {
    @NotEmpty(message = "name is not empty")
    private String name;
    @NotEmpty(message = "username is not empty")
    private String username;
    @Email(message = "not email")
    private String email;
    @NotEmpty
    @Size( min = 8, message = "password should have at least 10 characters")
    private String password;
}
