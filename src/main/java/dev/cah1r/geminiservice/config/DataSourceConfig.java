package dev.cah1r.geminiservice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("!test")
public class DataSourceConfig {

  @Value(value = "${spring.datasource.username}")
  private String username;

  @Value(value = "${spring.datasource.password}")
  private String password;

  @Value(value = "${spring.datasource.url}")
  private String url;


  @Bean
  public HikariDataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName("org.postgresql.Driver");
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);

    config.setMaximumPoolSize(10);
    config.setConnectionTimeout(30000);
    config.setIdleTimeout(600000);

    return new HikariDataSource(config);
  }
}
