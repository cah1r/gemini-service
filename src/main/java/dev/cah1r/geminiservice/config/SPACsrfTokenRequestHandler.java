package dev.cah1r.geminiservice.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.util.Arrays;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

@Slf4j
final class SPACsrfTokenRequestHandler extends CsrfTokenRequestAttributeHandler {

  private static final String CSRF_NAME = "XSRF-TOKEN";

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfTokenSupplier) {
    ofNullable(csrfTokenSupplier.get()).ifPresent(csrfToken -> {
      String headerToken = request.getHeader(csrfToken.getHeaderName());
      String cookieToken = getCsrfTokenFromCookie(request);

      if (headerToken != null && headerToken.equals(cookieToken)) {
        super.handle(request, response, csrfTokenSupplier);
      } else {
        throw new CsrfException("Invalid CSRF token");
      }
    });
  }

  private String getCsrfTokenFromCookie(HttpServletRequest request) {
    return ofNullable(request.getCookies())
        .flatMap(cookies -> Arrays.stream(cookies)
            .filter(cookie -> CSRF_NAME.equals(cookie.getName()))
            .findFirst()
            .map(Cookie::getValue))
        .orElse(null);
  }
}
