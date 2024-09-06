package dev.cah1r.geminiservice.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppAuthController {

  @PostMapping("/api/v1/auth/csrf")
  ResponseEntity<String> initCsrf() {
    return ResponseEntity.ok().build();
  }
}
