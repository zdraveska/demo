package com.example.demo.mapper;

import com.example.demo.domain.users.Name;
import com.example.demo.domain.users.User;
import com.example.demo.domain.users.UserRole;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.input.user.CreateUserDto;
import com.example.demo.dto.input.user.UpdateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    //    @Mapping(target = "image", expression = "java(com.valtech.gourmet.mk.valtechgourmet.util.ImageUtil.decompressBytes(user.getImage()))")
    @Mapping(target = "image")
    UserDto toDto(User user);

    //    @Mapping(target = "status", constant = "ACTIVE")
//    @Mapping(target = "userRole", constant = "USER")
    @Mapping(target = "name", source = "createUserDto", qualifiedByName = "createFullName")
    User createUserDtoToEntity(CreateUserDto createUserDto);

    List<UserDto> listToDto(List<User> usersList);

    @Mapping(target = "user.name", source = "updateUserDto", qualifiedByName = "createFullName")
    User updateDtoToEntity(@MappingTarget User user, UpdateUserDto updateUserDto);

    @Named("createFullName")
    default Name map(UpdateUserDto updateUserDto) {
        return new Name(updateUserDto.getName(), updateUserDto.getSurname());
    }

    @Named("createFullName")
    default Name map(CreateUserDto createUserDto) {
        return new Name(createUserDto.getName(), createUserDto.getSurname());
    }

    LoginResponseDto toLoginResponseDto(UserDto userDto, UserRole role);
}
