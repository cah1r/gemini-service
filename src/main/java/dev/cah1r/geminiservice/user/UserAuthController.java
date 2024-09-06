package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.user.dto.CreateUserDto;
import dev.cah1r.geminiservice.user.dto.UserCredentialsDto;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.function.Tuple2;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthController {

  private final UserAuthService userAuthService;

  @PostMapping("/login")
  ResponseEntity<UserDataDto> login(@RequestBody UserCredentialsDto dto) {
    var userWithJwt = userAuthService.login(dto);

    return getOkResponse(userWithJwt);
  }

  @PostMapping("/signup")
  ResponseEntity<UserDataDto> createUser(@RequestBody CreateUserDto createUserDto) {
    var userWithToken = userAuthService.signUp(createUserDto);

    return getOkResponse(userWithToken);
  }

  private static ResponseEntity<UserDataDto> getOkResponse(Tuple2<UserDataDto, String> userWithToken) {
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userWithToken.getT2())
        .body(userWithToken.getT1());
  }
}
