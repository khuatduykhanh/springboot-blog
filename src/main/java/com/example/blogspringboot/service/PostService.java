package com.example.blogspringboot.service;

import com.example.blogspringboot.dto.PostDto;
import com.example.blogspringboot.dto.PostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDto createPost(PostDto postdto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto,Long id);
    void  deletePost(Long id);
}
