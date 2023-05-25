package com.example.demo.service.impl;

import com.example.demo.domain.common.Image;
import com.example.demo.domain.common.UrlToken;
import com.example.demo.domain.exceptions.DuplicateEmailException;
import com.example.demo.domain.exceptions.IncorrectPassword;
import com.example.demo.domain.exceptions.InvalidPasswordException;
import com.example.demo.domain.exceptions.UserNotFoundException;
import com.example.demo.domain.users.User;
import com.example.demo.domain.users.UserRole;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.input.user.ChangeUserPasswordDto;
import com.example.demo.dto.input.user.CreateUserDto;
import com.example.demo.dto.input.user.PasswordDto;
import com.example.demo.dto.input.user.UpdateUserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.util.ImageUtil.checkIfImageIsValid;
import static com.example.demo.util.ImageUtil.compressMultipartFile;
import static com.example.demo.util.PermissionUtil.*;
import static com.example.demo.util.ValidationUtil.checkIfAlphabetic;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ImageRepository imageRepository;
  private final UserMapper userMapper;
//  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  static final String RESET_PASSWORD_PATH = "/reset-password?token=";

public UserServiceImpl(UserRepository userRepository,
                       ImageRepository imageRepository, UserMapper userMapper,
                       @Lazy PasswordEncoder passwordEncoder,
                       TokenService tokenService) {
  this.userRepository = userRepository;
  this.imageRepository = imageRepository;
  this.userMapper = userMapper;
  this.passwordEncoder = passwordEncoder;
  this.tokenService = tokenService;
}

  @CacheEvict(cacheNames = {"users"}, allEntries = true)
  @Override
  public UserDto createUser(CreateUserDto createUserDto) {
    if (userRepository.findByEmail(createUserDto.getEmail()).isPresent()) {
      throw new DuplicateEmailException(createUserDto.getEmail());
    }

    checkIfAlphabetic(createUserDto.getName(), createUserDto.getSurname());

    User user = userMapper.createUserDtoToEntity(createUserDto);
    user = userRepository.save(user);
    log.debug("User {} created", user);

//    String tokenValue = tokenService.createUrlToken(null, user).getToken();
//    String url = tokenService.generateEmailUrl(tokenValue, RESET_PASSWORD_PATH);
//
//    EmailDto emailDto = generateEmail(createUserDto.getEmail(), createUserDto.getName(), createUserDto.getSurname(),
//        EmailType.CREATE_USER, url);
//    emailService.sendSimpleEmail(emailDto, EmailType.CREATE_USER);
    return userMapper.toDto(user);
  }

  @Cacheable(cacheNames = {"users"}, key = "#id")
  @Override
  public UserDto findById(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    log.debug("Find user by id: " + id);
    return userMapper.toDto(user);
  }

  @Cacheable(cacheNames = {"users"})
  @Override
  public List<UserDto> listAllUsers(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    log.debug("List all users");
    return userMapper.listToDto(userRepository.findAllByOrderByNameAsc(pageable).stream().toList());
//        .sorted(Comparator.comparing(User::getStatus)).collect(Collectors.toList()));
  }

  @Cacheable(cacheNames = {"users"}, key = "#email")
  @Override
  public Optional<User> findByEmail(String email) {
    log.debug("Finding user with username {}", email);
    return userRepository.findByEmail(email);
  }

  @Cacheable(cacheNames = {"users"}, key = "#username")
  @Override
  public Optional<User> findByUsername(String username) {
    log.debug("Finding user with username {}", username);
    return userRepository.findByUsername(username);
  }

  @Override
  public List<UserRole> listAllRoles() {
    log.debug("List all roles");
    return Arrays.stream(UserRole.values()).collect(Collectors.toList());
  }

  @Cacheable(cacheNames = {"user"})
  @Override
  public UserDto getMyProfile() {
    String username = getUsername();
    log.debug("Get my profile");
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    return userMapper.toDto(user);
  }

  @Override
  public UserDto changePassword(ChangeUserPasswordDto changeUserPasswordDto) {
    String email = getUsername();
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    if (!passwordEncoder.matches(changeUserPasswordDto.getOldPassword(), user.getPassword())) {
      throw new IncorrectPassword();
    }

    user.setPassword(passwordEncoder.encode(changeUserPasswordDto.getNewPassword()));
    log.debug("Change password");
    return userMapper.toDto(userRepository.save(user));
  }

  @Override
  public Integer checkPasswordComplexity(PasswordDto passwordDto) {
    Zxcvbn zxcvbn = new Zxcvbn();
    String password = passwordDto.getPassword();
    Strength strength = zxcvbn.measure(password);
    int passwordScore = strength.getScore();
    if (password.length() < 8 || passwordScore < 2) {
      throw new InvalidPasswordException();
    }
    log.debug("Password checked for complexity");
    return passwordScore;
  }

  @CacheEvict(cacheNames = {"users", "user"}, key = "#id", allEntries = true)
  @Override
  public void uploadImage(Long id, MultipartFile multipartFile) {
    log.debug("Upload image");
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    checkIfImageIsValid(multipartFile);
    checkUserUploadImagePermission(user.getEmail(), id);

    Image image = Image.builder()
            .image(compressMultipartFile(multipartFile))
            .imageContentType(multipartFile.getContentType())
            .imageName(multipartFile.getOriginalFilename())
            .build();
    image = imageRepository.save(image);
    user.setImage(image);
    userRepository.save(user);
  }

  @CacheEvict(cacheNames = {"users", "user"}, key = "#id", allEntries = true)
  @Override
  public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
    User user = this.userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
    checkIfAlphabetic(updateUserDto.getName(), updateUserDto.getSurname());
    checkUserEditPermission(user.getEmail(), id, updateUserDto.getEmail());

    User saved = userRepository.save(userMapper.updateDtoToEntity(user, updateUserDto));
    log.debug("User {} updated", saved);
    return userMapper.toDto(saved);
  }

//  @Override
//  public void requestResetPassword(String email) {
//    log.debug("Request for reset password");
//    User user = findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
//    String tokenValue = tokenService.createUrlToken(null, user).getToken();
//    String url = tokenService.generateEmailUrl(tokenValue, RESET_PASSWORD_PATH);
//    EmailDto emailDto = generateEmail(email, user.getName(), user.getSurname(), EmailType.RESET_PASSWORD, url);
//    emailService.sendSimpleEmail(emailDto, EmailType.RESET_PASSWORD);
//  }

  @Override
  public UserDto resetPassword(PasswordDto passwordDto, String tokenValue) {
    UrlToken token = tokenService.findByToken(tokenValue);
    User user = token.getUser();
    user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
    log.debug("Reset password");
    return userMapper.toDto(userRepository.save(user));
  }

}
