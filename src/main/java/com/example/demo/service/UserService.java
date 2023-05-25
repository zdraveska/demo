package com.example.demo.service;

import com.example.demo.domain.users.User;
import com.example.demo.domain.users.UserRole;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.input.user.ChangeUserPasswordDto;
import com.example.demo.dto.input.user.CreateUserDto;
import com.example.demo.dto.input.user.PasswordDto;
import com.example.demo.dto.input.user.UpdateUserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

  UserDto createUser(CreateUserDto userDto);

  UserDto findById(Long id);

  List<UserDto> listAllUsers(Integer page, Integer size);

  UserDto updateUser(Long id, UpdateUserDto updateUserDto);


  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  List<UserRole> listAllRoles();

  UserDto getMyProfile();

//  void requestResetPassword(String email);

  UserDto resetPassword(PasswordDto passwordDto, String token);

  UserDto changePassword(ChangeUserPasswordDto changeUserPasswordDto);

  Integer checkPasswordComplexity(PasswordDto passwordDto);

  void uploadImage(Long id, MultipartFile multipartFile);

}