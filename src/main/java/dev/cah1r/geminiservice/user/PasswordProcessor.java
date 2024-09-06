package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.error.exception.InvalidPasswordException;
import dev.cah1r.geminiservice.user.dto.UserCredentialsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PasswordProcessor {

  private final PasswordEncoder passwordEncoder;

  void validate(UserCredentialsDto dto, User user) {
    if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
      throw new InvalidPasswordException(dto.email());
    }
  }

  String encrypt(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}
