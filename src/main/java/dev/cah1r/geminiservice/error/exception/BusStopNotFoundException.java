package dev.cah1r.geminiservice.error.exception;

import static java.lang.String.format;

public class BusStopNotFoundException extends EntityNotFoundException {
  public BusStopNotFoundException(Long id) {
    super(format("Couldn't find bus stop with id: %s in database", id));
  }
}
