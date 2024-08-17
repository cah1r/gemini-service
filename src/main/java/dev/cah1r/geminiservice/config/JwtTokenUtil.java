package dev.cah1r.geminiservice.config;

import dev.cah1r.geminiservice.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

  private final String secretKey;
  private final int tokenValidity;

  JwtTokenUtil(
      @Value("${app.security.jwt.signing-key}") String secretKey,
      @Value("${app.security.jwt.validity}") String tokenValidity
  ) {
    this.secretKey = secretKey;
    this.tokenValidity = Integer.parseInt(tokenValidity);
  }

  public String generateToken(String email, Role role) {
    var claims = Jwts.claims()
        .subject(email)
        .add("role", role)
        .build();

    return Jwts.builder()
        .signWith(getSecretKey(), Jwts.SIG.HS512)
        .claims(claims)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + tokenValidity))
        .compact();
  }

  public String getEmailFromToken(String token) {
    return getTokenPayload(token).getSubject();
  }

  public boolean validateToken(String token) {
    var expiration = getTokenPayload(token).getExpiration();
    return expiration.after(new Date());
  }

  private Claims getTokenPayload(String token) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}
