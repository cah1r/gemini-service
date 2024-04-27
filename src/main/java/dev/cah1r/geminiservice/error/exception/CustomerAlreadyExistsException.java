package dev.cah1r.geminiservice.error.exception;

public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException(String email) {
        super(String.format("Customer with email: %s already exists in database", email));
    }
}
