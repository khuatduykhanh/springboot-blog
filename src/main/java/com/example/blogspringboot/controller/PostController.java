package com.example.blogspringboot.controller;

import com.example.blogspringboot.dto.PostDto;
import com.example.blogspringboot.dto.PostResponse;
import com.example.blogspringboot.service.PostService;
import com.example.blogspringboot.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping()
//    public List<PostDto> getAllPost(){
//        return postServiceImpl.getAllPost();
//    }
    public PostResponse getAllPost(@RequestParam(name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                   @RequestParam(name = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                   @RequestParam(name = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                   @RequestParam(name = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir // sortDir sắp xếp tăng hay giảm
                                     ){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost (@PathVariable("id") Long id,@Valid @RequestBody PostDto postDto){
            PostDto newPost = postService.updatePost(postDto,id);
            return ResponseEntity.ok(newPost);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }
}
