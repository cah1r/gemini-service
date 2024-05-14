package dev.cah1r.geminiservice.error.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusStopAlreadyExistsException extends RuntimeException {
    public BusStopAlreadyExistsException(String city, String description) {
        super(String.format("Couldn't create bus stop in %s with description: %s. It already exists in database", city, description));
    }
}
