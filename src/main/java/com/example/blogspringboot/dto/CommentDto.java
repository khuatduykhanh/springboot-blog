package com.example.blogspringboot.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty(message = "name not empty")
    private String name;
    @NotEmpty()
    @Email(message = "Email format is incorrect")
    private String email;
    @NotEmpty()
    @Size(min = 10, message = "content should have at least 10 characters")
    private String content;
}
