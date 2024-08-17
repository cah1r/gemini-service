package dev.cah1r.geminiservice.error.exception;

import static java.lang.String.format;

public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException(String email) {
    super(format("Password provided by user: %s is incorrect", email));
  }
}
