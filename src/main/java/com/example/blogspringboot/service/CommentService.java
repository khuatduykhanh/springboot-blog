package com.example.blogspringboot.service;

import com.example.blogspringboot.dto.CommentDto;
import com.example.blogspringboot.dto.PostDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getAllCommentByPostId(Long postId);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
    void  deleteComment(Long postId, Long commentId);
}
