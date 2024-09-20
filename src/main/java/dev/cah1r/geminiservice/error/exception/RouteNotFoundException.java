package dev.cah1r.geminiservice.error.exception;

import java.util.UUID;

public class RouteNotFoundException extends EntityNotFoundException {

  public RouteNotFoundException(UUID id) {
    super(id.toString());
  }

  public RouteNotFoundException(String message) {
    super(message);
  }
}
