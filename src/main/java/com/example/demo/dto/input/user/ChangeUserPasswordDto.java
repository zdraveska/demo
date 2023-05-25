package com.example.demo.dto.input.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeUserPasswordDto {

  @NotNull
  @Size(min = 1, max = 50)
  String oldPassword;

  @NotNull
  @Size(min = 1, max = 50)
  String newPassword;
}
