package dev.cah1r.geminiservice.error.exception;

public class CarNotFoundException extends EntityNotFoundException {
  public CarNotFoundException(String message) {
    super(message);
  }
}
