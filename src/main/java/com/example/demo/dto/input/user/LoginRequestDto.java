package com.example.demo.dto.input.user;

import com.example.demo.domain.annotation.ValidEmail;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginRequestDto {

  @NotNull
  @ValidEmail
  private String username;

  @NotNull
  private String password;

}