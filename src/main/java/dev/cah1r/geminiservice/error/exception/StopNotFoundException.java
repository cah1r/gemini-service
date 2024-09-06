package dev.cah1r.geminiservice.error.exception;

import java.util.List;

import static java.lang.String.format;

public class StopNotFoundException extends EntityNotFoundException {
  public StopNotFoundException(Long id) {
    super(format("Couldn't find bus stop with id: %s in database", id));
  }

  public StopNotFoundException(List<Long> ids) {
    super(format("Couldn't find bus stops with ids: %s", ids));
  }
}
