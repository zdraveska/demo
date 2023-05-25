package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.dto.input.shop.UpdatePostDto;
import com.example.demo.dto.input.shop.CreatePostDto;

public interface PostService {

  PostDto createPost(CreatePostDto createPostDto);

  void deletePost(Long id);

  PostDto updatePost(UpdatePostDto updatePostDto, Long postId);
}