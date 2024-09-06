package dev.cah1r.geminiservice.error.exception;

import java.util.UUID;

public class DriverNotFoundException extends EntityNotFoundException {

  public DriverNotFoundException(UUID id) {
    super("Couldn't find driver with id: " + id);
  }
}
