package com.security.service;

import com.security.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostDto getPostById(Long postId);
    PostDto updatePost(PostDto postDto,Long postId);
    List<PostDto> getAllPosts();


}
