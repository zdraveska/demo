package com.example.demo.mapper;

import com.example.demo.domain.common.UrlToken;
import com.example.demo.domain.users.AccountRequest;
import com.example.demo.domain.users.User;
import com.example.demo.dto.UrlTokenDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface TokenMapper {

  UrlTokenDto toDto(UrlToken token);

  @Mapping(target = "token", source = "tokenValue")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "expiration", source = "expiration", qualifiedByName = "mapExpirationValue")
  @Mapping(target = "accountRequest", source = "accountRequest")
  @Mapping(target = "id", ignore = true)
  UrlToken toEntity(String tokenValue, User user, int expiration, AccountRequest accountRequest);

  @Named("mapExpirationValue")
  default LocalDateTime map(int expiration) {
    return LocalDateTime.now().plusMinutes(expiration);
  }
}
