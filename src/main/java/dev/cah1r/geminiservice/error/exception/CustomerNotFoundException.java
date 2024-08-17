package dev.cah1r.geminiservice.error.exception;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException(String email) {
        super("Didn't find in database customer with email: " + email);
    }
}
