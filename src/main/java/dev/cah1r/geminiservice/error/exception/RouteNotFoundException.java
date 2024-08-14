package dev.cah1r.geminiservice.error.exception;

import static java.lang.String.format;

public class RouteNotFoundException extends RuntimeException {
  public RouteNotFoundException(String initialStop, String finalStop) {
    super(format("Couldn't find route between %s and %s", initialStop, finalStop));
  }
}
