package com.security.controller;

import com.security.dto.PostDto;
import com.security.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto crestedPost=postService.createPost(postDto);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(crestedPost);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        return  ok(postService.getPostById(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable Long postId) {

        return ok(
                postService.updatePost(postDto, postId)
        );
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
