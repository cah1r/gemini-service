package dev.cah1r.geminiservice.error.exception;

public class UserAlreadyExistsException extends EntityAlreadyExistsException {

  public UserAlreadyExistsException(String email) {
    super(String.format("Couldn't create new user with email: %s as user with this email already exists", email));
  }
}
