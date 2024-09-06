package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.error.exception.CustomerNotFoundException;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public UserDataDto findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .map(UserMapper::toUserDataDto)
        .orElseThrow(() -> new CustomerNotFoundException(email));
  }
}