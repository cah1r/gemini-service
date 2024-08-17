package dev.cah1r.geminiservice.error.exception;

public class LineAlreadyExistsException extends EntityAlreadyExistsException {

    public LineAlreadyExistsException(String description) {
        super(String.format("Line with description: %s already exists in database", description));
    }
}
