package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.config.JwtTokenUtil;
import dev.cah1r.geminiservice.error.exception.CustomerAlreadyExistsException;
import dev.cah1r.geminiservice.error.exception.CustomerNotFoundException;
import dev.cah1r.geminiservice.error.exception.InvalidPasswordException;
import dev.cah1r.geminiservice.security.CreateUserDto;
import dev.cah1r.geminiservice.security.UserCredentialsDto;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtTokenUtil jwtTokenUtil;
  private final PasswordEncoder passwordEncoder;

  public UserDataDto createGoogleUser(CreateUserDto createUserDto) {
    var user = userRepository.findByEmail(createUserDto.email()).orElseGet(() -> {
      var encryptedPassword = passwordEncoder.encode(createUserDto.password());
      var savedUser = userRepository.save(UserMapper.toUser(createUserDto, encryptedPassword));
      log.info("User with email: {} has been saved in db with id: {}", savedUser.getEmail(), savedUser.getId());
      return savedUser;
    });

    return UserMapper.toUserDataDto(user);
  }

  List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public UserDataDto findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .map(UserMapper::toUserDataDto)
        .orElseThrow(() -> new CustomerNotFoundException(email));
  }

  public Tuple2<UserDataDto, String> login(UserCredentialsDto dto) {
    var user = userRepository.findByEmail(dto.email())
        .orElseThrow(() -> new CustomerNotFoundException(dto.email()));

    validatePassword(dto, user);
    var token = jwtTokenUtil.generateToken(user.getEmail(), user.getRole());

    return Tuples.of(UserMapper.toUserDataDto(user), token);
  }

  private void validatePassword(UserCredentialsDto dto, User user) {
    if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
      throw new InvalidPasswordException(dto.email());
    }
  }

  public Tuple2<UserDataDto, String> signUp(CreateUserDto createUserDto) {
    userRepository.findByEmail(createUserDto.email()).ifPresent(
        user -> {
          log.info("Failed to create new user with email: {} as it already exists in db", createUserDto.email());
          throw new CustomerAlreadyExistsException(user.getEmail());
        });

    String encryptedPassword = passwordEncoder.encode(createUserDto.password());
    User createdUser = userRepository.save(UserMapper.toUser(createUserDto, encryptedPassword));
    String token = jwtTokenUtil.generateToken(createUserDto.email(), createdUser.getRole());
    log.info("Created new user with email: {}", createdUser.getEmail());

    return Tuples.of(UserMapper.toUserDataDto(createdUser), token);
  }
}