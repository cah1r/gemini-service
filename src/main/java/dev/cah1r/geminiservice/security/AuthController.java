package dev.cah1r.geminiservice.security;

import dev.cah1r.geminiservice.user.UserService;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuple2;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final UserService userService;

  @PostMapping("/csrf")
  ResponseEntity<String> initCsrf() {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  ResponseEntity<UserDataDto> login(@RequestBody UserCredentialsDto dto) {
    var userWithJwt = userService.login(dto);

    return getOkResponse(userWithJwt);
  }

  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<UserDataDto> createCustomer(@RequestBody CreateUserDto createUserDto) {
    var userWithToken = userService.signUp(createUserDto);

    return getOkResponse(userWithToken);
  }

  private static ResponseEntity<UserDataDto> getOkResponse(Tuple2<UserDataDto, String> userWithToken) {
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userWithToken.getT2())
        .body(userWithToken.getT1());
  }
}
