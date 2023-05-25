package com.example.demo.dto.input.user;

import com.example.demo.domain.annotation.ValidEmail;
import com.example.demo.domain.annotation.ValidPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

  @NotBlank
  @Size(min = 1, max = 50)
  String name;

  @NotBlank
  @Size(min = 1, max = 50)
  String surname;

  @ValidEmail
  @NotNull
  @Size(min = 15, max = 128)
  String email;

  @NotBlank
  @Size(min = 1, max = 50)
  String username;

  @NotBlank
  @NotEmpty
  @ValidPhoneNumber
  @Size(min = 1, max = 50)
  String phoneNumber;
}