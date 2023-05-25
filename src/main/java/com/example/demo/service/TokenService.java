package com.example.demo.service;


import com.example.demo.domain.common.UrlToken;
import com.example.demo.domain.users.AccountRequest;
import com.example.demo.domain.users.User;
import com.example.demo.dto.UrlTokenDto;

public interface TokenService {

  UrlTokenDto createUrlToken(AccountRequest accountRequest, User user);

  String generateEmailUrl(String token, String pathUrl);

  UrlToken findByToken(String tokenValue);

}
