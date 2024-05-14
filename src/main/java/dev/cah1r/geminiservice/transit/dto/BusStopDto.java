package dev.cah1r.geminiservice.transit.dto;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.transit.BusStop;

public record BusStopDto(@NonNull String id, @NonNull String city, @NonNull String details) {

  public static BusStopDto toBusStopDto(BusStop busStop) {
    return new BusStopDto(busStop.getId(), busStop.getCity(), busStop.getDetails());
  }
}
