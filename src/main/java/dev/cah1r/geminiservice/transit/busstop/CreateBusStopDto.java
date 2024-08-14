package dev.cah1r.geminiservice.transit.busstop;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.transit.Line;

import java.util.List;

public record CreateBusStopDto(
        @NonNull String city,
        @NonNull String details,
        boolean isTicketAvailable,
        String lineId
) {

  public static BusStop toBusStop(CreateBusStopDto busStopDto, Line line) {
    return BusStop.builder()
        .city(busStopDto.city())
        .details(busStopDto.details())
        .isTicketAvailable(busStopDto.isTicketAvailable())
        .lines(List.of(line))
        .build();
  }
}
