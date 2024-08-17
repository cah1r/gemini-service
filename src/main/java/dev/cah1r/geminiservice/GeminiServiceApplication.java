package dev.cah1r.geminiservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Slf4j
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class GeminiServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(GeminiServiceApplication.class, args);
  }
}
