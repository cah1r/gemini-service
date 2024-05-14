package dev.cah1r.geminiservice.error.exception;

public class CreateRouteException extends RuntimeException {
  public CreateRouteException(Throwable err) {
    super("Couldn't create new route.", err);
  }
}
