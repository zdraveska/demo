package com.example.demo.service.impl;

import com.example.demo.domain.common.UrlToken;
import com.example.demo.domain.users.AccountRequest;
import com.example.demo.domain.users.User;
import com.example.demo.mapper.TokenMapper;
import com.example.demo.service.TokenService;
import com.example.demo.domain.exceptions.TokenIsNotValidException;
import com.example.demo.domain.exceptions.TokenNotFoundException;
import com.example.demo.dto.UrlTokenDto;
import com.example.demo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

  private final TokenRepository tokenRepository;
  private final int expiration;
  private final String baseUrl;
  private final TokenMapper tokenMapper;

  public TokenServiceImpl(TokenRepository tokenRepository,
      @Value("${application.url.baseUrl}") String baseUrl,
      @Value("${application.url.token.expiration}") int expiration,
      TokenMapper tokenMapper) {
    this.tokenRepository = tokenRepository;
    this.expiration = expiration;
    this.baseUrl = baseUrl;
    this.tokenMapper = tokenMapper;
  }

  @Override
  public UrlTokenDto createUrlToken(AccountRequest accountRequest, User user) {
    String tokenValue = UUID.randomUUID().toString();
    UrlToken token = tokenMapper.toEntity(tokenValue, user, expiration, accountRequest);

    token = tokenRepository.save(token);
    return tokenMapper.toDto(token);
  }

  @Override
  public String generateEmailUrl(String token, String pathUrl) {
    return baseUrl + pathUrl + token;
  }

  @Override
  public UrlToken findByToken(String tokenValue) {
    UrlToken token = tokenRepository.findByToken(tokenValue).orElseThrow(TokenNotFoundException::new);
    if (!token.isValid()) {
      throw new TokenIsNotValidException();
    }
    return token;
  }
}
