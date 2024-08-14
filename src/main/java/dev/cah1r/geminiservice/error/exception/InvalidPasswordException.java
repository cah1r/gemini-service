package dev.cah1r.geminiservice.error.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String email) {
        super(String.format("Invalid password provided by user: %s.", email));
    }
}
