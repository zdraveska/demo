package com.example.demo.dto;

import com.example.demo.domain.users.AccountRequest;
import com.example.demo.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlTokenDto {

  String token;

  User user;

  LocalDateTime expiration;

  AccountRequest accountRequest;

}
