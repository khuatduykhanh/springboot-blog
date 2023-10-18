package com.example.blogspringboot.service.impl;

import com.example.blogspringboot.dto.CommentDto;
import com.example.blogspringboot.entity.Comment;
import com.example.blogspringboot.entity.Post;
import com.example.blogspringboot.exception.BlogAPIException;
import com.example.blogspringboot.exception.ResourceNotFoundException;
import com.example.blogspringboot.repository.CommentRepository;
import com.example.blogspringboot.repository.PostRepository;
import com.example.blogspringboot.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment cmt = convertComment(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        cmt.setPost(post);
        Comment newcomment = commentRepository.save(cmt);
        return convertCommentDto(newcomment);
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(Long postId) {
        List<Comment> listComment =  commentRepository.findByPostId(postId);
        return listComment.stream().map(this::convertCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        Comment cmt = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!cmt.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }
        return convertCommentDto(cmt);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        Comment cmt = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!cmt.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }
        cmt.setName(commentDto.getName());
        cmt.setEmail(commentDto.getEmail());
        cmt.setContent(commentDto.getContent());
        Comment newCmt = commentRepository.save(cmt);
        return convertCommentDto(newCmt);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        Comment cmt = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!cmt.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }
        commentRepository.delete(cmt);



    }
    private CommentDto convertCommentDto(Comment comment){

        return modelMapper.map(comment,CommentDto.class);
    }
    private Comment convertComment(CommentDto commentDto){

        return modelMapper.map(commentDto,Comment.class);
    }
}
