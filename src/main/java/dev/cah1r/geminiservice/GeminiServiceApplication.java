package dev.cah1r.geminiservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Slf4j
@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
public class GeminiServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(GeminiServiceApplication.class, args);
  }
}
