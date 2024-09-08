package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.error.exception.CustomerNotFoundException;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  List<UserDataDto> getAllUsers() {
    return userRepository.findAll()
        .stream()
        .map(UserMapper::toUserDataDto)
        .toList();
  }

  public UserDataDto findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .map(UserMapper::toUserDataDto)
        .orElseThrow(() -> new CustomerNotFoundException(email));
  }
}