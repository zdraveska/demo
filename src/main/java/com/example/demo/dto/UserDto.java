package com.example.demo.dto;

import com.example.demo.domain.common.Image;
import com.example.demo.domain.users.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  Long id;

  Name name;

  String surname;

  String email;

  Image image;

}