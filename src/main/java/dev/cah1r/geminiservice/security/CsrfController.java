package dev.cah1r.geminiservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1")
@RestController
class CsrfController {

  @PostMapping("/csrf")
  ResponseEntity<String> getToken() {
    return ResponseEntity.ok().build();
  }
}
