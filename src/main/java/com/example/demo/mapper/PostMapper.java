package com.example.demo.mapper;

import com.example.demo.domain.shop.Post;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.input.shop.CreatePostDto;
import com.example.demo.dto.input.shop.UpdatePostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post createDtoToEntity(CreatePostDto createPostDto);

    PostDto toDto(Post post);

    Post updateDtoToEntity(@MappingTarget Post post, UpdatePostDto updatePostDto);

}
