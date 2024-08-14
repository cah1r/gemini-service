package dev.cah1r.geminiservice.error.exception;

public class LineAlreadyExistsException extends RuntimeException {
    public LineAlreadyExistsException(String name) {
        super("Line with name: {} already exists in database");
    }
}
