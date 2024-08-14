package dev.cah1r.geminiservice.error;

import static org.springframework.http.HttpStatus.*;

import dev.cah1r.geminiservice.error.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomerAlreadyExistsException.class)
  public ResponseEntity<String> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex) {
    return ResponseEntity.status(CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(FoundNullValuesInCustomerData.class)
  public ResponseEntity<String> handleNullInCustomerData(FoundNullValuesInCustomerData ex) {
    return ResponseEntity.status(BAD_REQUEST).body("Found null value in customer data registration");
  }

  @ExceptionHandler(CreateRouteException.class)
  public ResponseEntity<String> handleCreateRouteException(CreateRouteException ex) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Couldn't create route. " + ex.getMessage());
  }

  @ExceptionHandler(BusStopAlreadyExistsException.class)
  public ResponseEntity<String> handleBusStopAlreadyExistsException(BusStopAlreadyExistsException ex) {
    log.error(ex.getMessage());
    return ResponseEntity.status(CONFLICT).body(String.format(ex.getMessage()));
  }

  @ExceptionHandler(UpdateException.class)
  public ResponseEntity<String> handleUpdateException(UpdateException ex) {
    return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex) {
    log.info(ex.getMessage());
    return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(RouteNotFoundException.class)
  public ResponseEntity<String> handleRouteNotFoundException(RouteNotFoundException ex) {
    log.info(ex.getMessage());
    return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
  }
}
