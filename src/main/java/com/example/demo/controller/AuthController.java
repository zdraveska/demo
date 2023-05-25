package com.example.demo.controller;

import com.example.demo.domain.exceptions.TokenIsNotValidException;
import com.example.demo.domain.exceptions.UserNotFoundException;
import com.example.demo.domain.users.User;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.input.user.LoginRequestDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.cookies.CookieUtil;
import com.example.demo.security.jwt.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final CookieUtil cookieUtil;
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(HttpServletResponse response, @RequestBody @Valid LoginRequestDto loginRequest) {
    try {
      Authentication authenticate = authenticationManager
          .authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
      User user = (User) authenticate.getPrincipal();
      String token = jwtTokenUtil.generateToken(user);
      String refresh = jwtTokenUtil.generateRefreshToken(user);

      response.addCookie(cookieUtil.createTokenCookie(token));
      response.addCookie(cookieUtil.createRefreshTokenCookie(refresh));
      return ResponseEntity.ok()
          .header(HttpHeaders.AUTHORIZATION, token)
          .body(userMapper.toLoginResponseDto(userMapper.toDto(user), user.getUserRole()));
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @CacheEvict(cacheNames = {"user"}, allEntries = true)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @PostMapping("/logout")
  public ResponseEntity<Void> logoutUser(HttpServletRequest request, HttpServletResponse response) {
    Cookie tokenCookie = cookieUtil.deleteTokenCookie();
    Cookie refreshTokenCookie = cookieUtil.deleteRefreshTokenCookie();
    response.addCookie(tokenCookie);
    response.addCookie(refreshTokenCookie);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @PostMapping("/refresh")
  public ResponseEntity<LoginResponseDto> refreshToken(
      @CookieValue(name = "refresh-token", required = false) String refreshToken, HttpServletResponse response) {
    if (!jwtTokenUtil.validate(refreshToken)) {
      throw new TokenIsNotValidException();
    }
    String username = jwtTokenUtil.getUsername(refreshToken);
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    String newToken = jwtTokenUtil.generateToken(user);
    response.addCookie(cookieUtil.createTokenCookie(newToken));
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, newToken)
        .body(userMapper.toLoginResponseDto(userMapper.toDto(user), user.getUserRole()));
  }

}