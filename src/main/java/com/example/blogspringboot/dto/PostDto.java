package com.example.blogspringboot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "description should have at least 10 characters")
    private String description;
    @NotEmpty
    @Size(min = 10, message = "content should have at least 10 characters")
    private String content;
    @NotEmpty
    private Set<CommentDto> comments;
}
