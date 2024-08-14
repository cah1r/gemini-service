package dev.cah1r.geminiservice.cutomer.account;

import dev.cah1r.geminiservice.cutomer.account.dto.LoginDataDto;
import dev.cah1r.geminiservice.error.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PasswordService {
    
    private final BCryptPasswordEncoder passwordEncoder;
    
    String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    void validatePassword(LoginDataDto loginData, String encryptedPassword) {
        if (!passwordEncoder.matches(loginData.password(), encryptedPassword)) {
            throw new InvalidPasswordException(loginData.email());
        }
    }
}
