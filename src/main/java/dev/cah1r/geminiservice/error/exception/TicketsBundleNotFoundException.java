package dev.cah1r.geminiservice.error.exception;

import java.util.UUID;

public class TicketsBundleNotFoundException extends EntityNotFoundException {

  public TicketsBundleNotFoundException(UUID id) {
    super("Couldn't find tickets bundle with id " + id);
  }
}
