package com.security.service.impl;

import com.security.dto.PostDto;
import com.security.entities.Post;
import com.security.entities.User;
import com.security.exception.ResourceNotFoundException;
import com.security.repository.PostRepository;
import com.security.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository  postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
       User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
       log.info("user {user}",user);
       Post post =  postRepository.findById(postId)
               .orElseThrow(()->new BadCredentialsException("Post Not Found with the id : "+postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post Not Found with the id : "+postId));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }
}
