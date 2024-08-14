package dev.cah1r.geminiservice.cutomer.account;

import dev.cah1r.geminiservice.cutomer.account.dto.CustomerDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleUserService {

  private final GoogleUserRepository googleUserRepository;

  Mono<CustomerDataDto> signInWithGoogle(GoogleUser googleUser) {
    return googleUserRepository
        .findGoogleUserByEmail(googleUser.getEmail())
        .doOnNext(customer -> log.info("User {} has been logged", customer.getEmail()))
        .map(CustomerMapper::toCustomerDataDto)
        .switchIfEmpty(saveUserInDb(googleUser));
  }

  private Mono<CustomerDataDto> saveUserInDb(GoogleUser googleUser) {
    return googleUserRepository.save(googleUser)
            .doOnNext(user -> log.info("Saved customer in database with email: {} and id: {}", user.getEmail(), user.getId()))
            .map(CustomerMapper::toCustomerDataDto);
  }
}
