package dev.cah1r.geminiservice.config;

import dev.cah1r.geminiservice.user.UserService;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String path = request.getRequestURI();
    try {
      if (!path.startsWith("/api/v1/auth")) {
        String authHeader = request.getHeader("Authorization");

        ofNullable(authHeader)
            .filter(header -> header.startsWith("Bearer"))
            .map(h -> h.split(" ")[1])
            .ifPresent(token -> {
              String email = jwtTokenUtil.getEmailFromToken(token);
              validateToken(email, token);
            });
      }
    } catch (ExpiredJwtException e) {
      SecurityContextHolder.clearContext();
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token has expired");
      return;
    }
    filterChain.doFilter(request, response);
  }


  private void validateToken(String email, String token) {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      var user = userService.findUserByEmail(email);
      if (jwtTokenUtil.validateToken(token)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, mapRoleToAuthority(user));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
  }

  private static List<SimpleGrantedAuthority> mapRoleToAuthority(UserDataDto user) {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.role().toString()));
  }

}
