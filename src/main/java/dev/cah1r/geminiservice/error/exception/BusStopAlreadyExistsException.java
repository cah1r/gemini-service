package dev.cah1r.geminiservice.error.exception;

import dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusStopAlreadyExistsException extends EntityAlreadyExistsException {
    public BusStopAlreadyExistsException(CreateStopDto createStopDto) {
        super(String.format("Couldn't create bus stop in %s with description: %s. It already exists in database",
                createStopDto.town(), createStopDto.details()
        ));
    }
}
