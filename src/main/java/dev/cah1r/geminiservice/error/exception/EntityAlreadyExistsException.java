package dev.cah1r.geminiservice.error.exception;

public class EntityAlreadyExistsException extends RuntimeException {
  EntityAlreadyExistsException(String message) {
    super(message);
  }
}
