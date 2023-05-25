package com.example.demo.service.impl;

import com.example.demo.domain.exceptions.PostNotFoundException;
import com.example.demo.domain.shop.Post;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.input.shop.CreatePostDto;
import com.example.demo.dto.input.shop.UpdatePostDto;
import com.example.demo.mapper.PostMapper;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;

  @Override
  public PostDto createPost(CreatePostDto createPostDto) {
    Post post = postMapper.createDtoToEntity(createPostDto);

    postRepository.save(post);
    log.debug("Post {} created", post);
    return postMapper.toDto(post);
  }

  @Override
  public void deletePost(Long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

    postRepository.delete(post);
    log.debug("Post has been deleted");
  }

  @Override
  public PostDto updatePost(UpdatePostDto updatePostDto, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

    post = postMapper.updateDtoToEntity(post, updatePostDto);
    postRepository.save(post);
    log.debug("Post {} modified", post);
    return postMapper.toDto(post);
  }

}