package com.example.blogspringboot.controller;

import com.example.blogspringboot.dto.CommentDto;
import com.example.blogspringboot.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getAllCommentByPostId(@PathVariable("postId") Long postId){
        return commentService.getAllCommentByPostId(postId);
    }
    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId,@Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId){
        commentService.deleteComment(postId,commentId);
        return ResponseEntity.ok("Delete successfully");
    }

}
