package dev.cah1r.geminiservice.config;

import dev.cah1r.geminiservice.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Profile("!test")
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(
      HttpSecurity http,
      JwtTokenUtil jwtTokenUtil,
      UserService userService
  ) throws Exception {

    http.cors(cors -> corsConfigurationSource())
        .csrf(csrf -> csrf
            .csrfTokenRepository(csrfTokenRepository())
            .csrfTokenRequestHandler(new SPACsrfTokenRequestHandler()))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/admin/**", "/api/v1/route/**").hasRole("ADMIN")
            .anyRequest()
            .permitAll())
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .addFilterBefore(jwtAuthFilter(jwtTokenUtil, userService), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "PUT", "DELETE", "PATCH"));
    configuration.setAllowedHeaders(List.of("X-XSRF-TOKEN", CONTENT_TYPE, AUTHORIZATION));
    configuration.setExposedHeaders(List.of(AUTHORIZATION));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  public CookieCsrfTokenRepository csrfTokenRepository() {
    var cookieRepo = CookieCsrfTokenRepository.withHttpOnlyFalse();
    cookieRepo.setCookiePath("/");
    cookieRepo.setCookieCustomizer(cookie -> cookie.sameSite("Strict"));

    return cookieRepo;
  }

  @Bean
  public JwtAuthFilter jwtAuthFilter(JwtTokenUtil jwtTokenUtil, UserService userService) {
    return new JwtAuthFilter(jwtTokenUtil, userService);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
