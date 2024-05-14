package dev.cah1r.geminiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoAuditing
@SpringBootApplication
@EnableReactiveMongoRepositories
public class GeminiServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(GeminiServiceApplication.class, args);
  }
}
