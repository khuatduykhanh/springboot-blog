package com.example.blogspringboot.service.impl;

import com.example.blogspringboot.dto.CommentDto;
import com.example.blogspringboot.dto.PostDto;
import com.example.blogspringboot.dto.PostResponse;
import com.example.blogspringboot.entity.Post;
import com.example.blogspringboot.exception.ResourceNotFoundException;
import com.example.blogspringboot.repository.PostRepository;
import com.example.blogspringboot.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
     public PostDto createPost (PostDto postdto){
        // convert dto sang entity

        Post post = convertPost(postdto);
         Post newPost =  postRepository.save(post);
         // convert entity sang dto
        return convertPostDto(newPost);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir){

        // câu lệnh này sẽ xem xét sortDir là asc hay des
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  // set up số trang, số lượng trong một trang và sắp xếp theo miền nào

        Page<Post> postList =  postRepository.findAll(pageable); // findAll với số trang và số lượng trang sẽ trong về Page

        List<Post> listOfPost = postList.getContent(); // lấy danh sách trang đã được phân trong postList

        List<PostDto> content = listOfPost.stream().map(this::convertPostDto).toList();
        PostResponse postResponse = new  PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(postList.getTotalElements());
        postResponse.setTotalPages(postList.getTotalPages());
        postResponse.setLast(postList.isLast());
        return postResponse;
    }
    private PostDto convertPostDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }
    private Post convertPost(PostDto postdto){
        return modelMapper.map(postdto,Post.class);
    }
    @Override
    public PostDto getPostById(Long id){
        Post post =  postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return convertPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
         Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));
         post.setTitle(postDto.getTitle());
         post.setDescription(postDto.getDescription());
         post.setContent(postDto.getContent());
         Post updatepost = postRepository.save(post);
         return convertPostDto(updatepost);
    }

    @Override
    public void deletePost(Long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));
        postRepository.delete(post);
    }


}
