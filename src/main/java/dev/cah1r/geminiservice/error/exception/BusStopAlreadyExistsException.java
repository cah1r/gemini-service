package dev.cah1r.geminiservice.error.exception;

import dev.cah1r.geminiservice.transit.busstop.CreateBusStopDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusStopAlreadyExistsException extends RuntimeException {
    public BusStopAlreadyExistsException(CreateBusStopDto createBusStopDto) {
        super(String.format("Couldn't create bus stop in %s with description: %s on line: {}. It already exists in database",
                createBusStopDto.city(), createBusStopDto.details(), createBusStopDto.lineId()
        ));
    }
}
