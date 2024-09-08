package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.config.JwtTokenUtil;
import dev.cah1r.geminiservice.error.exception.CustomerNotFoundException;
import dev.cah1r.geminiservice.error.exception.UserAlreadyExistsException;
import dev.cah1r.geminiservice.user.dto.CreateUserDto;
import dev.cah1r.geminiservice.user.dto.UserCredentialsDto;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserRepository userRepository;
  private final PasswordProcessor passwordProcessor;


  @Transactional
  public Tuple2<UserDataDto, String> signUp(CreateUserDto createUserDto) {
    userRepository.findByEmail(createUserDto.email()).ifPresent(
        user -> {
          log.info("Failed to create new user with email: {} as it already exists in db", createUserDto.email());
          throw new UserAlreadyExistsException(user.getEmail());
        });

    var encryptedPassword = passwordProcessor.encrypt(createUserDto.password());
    var createdUser = userRepository.save(UserMapper.toUser(createUserDto, encryptedPassword));
    var token = jwtTokenUtil.generateToken(createUserDto.email(), createdUser.getRole());
    log.info("New User with email: {} has been registered", createdUser.getEmail());

    return Tuples.of(UserMapper.toUserDataDto(createdUser), token);
  }

  public Tuple2<UserDataDto, String> login(UserCredentialsDto dto) {
    var user = userRepository.findByEmail(dto.email())
        .orElseThrow(() -> new CustomerNotFoundException(dto.email()));

    passwordProcessor.validate(dto, user);
    var token = jwtTokenUtil.generateToken(user.getEmail(), user.getRole());

    return Tuples.of(UserMapper.toUserDataDto(user), token);
  }
}
