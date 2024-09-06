package dev.cah1r.geminiservice.error.exception;

import static java.lang.String.format;

public class DriverAlreadyExistsException extends EntityAlreadyExistsException {
  public DriverAlreadyExistsException(String phoneNumber) {
    super(format("Driver with phone number: %s already exists in database", phoneNumber));
  }
}
