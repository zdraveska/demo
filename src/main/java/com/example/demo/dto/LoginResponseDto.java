package com.example.demo.dto;

import com.example.demo.domain.users.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {

  UserDto userDto;

  UserRole role;

}
