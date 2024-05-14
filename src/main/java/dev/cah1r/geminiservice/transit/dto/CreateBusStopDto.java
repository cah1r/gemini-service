package dev.cah1r.geminiservice.transit.dto;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.transit.BusStop;

public record CreateBusStopDto(@NonNull String city, @NonNull String details) {

  public static BusStop toBusStop(CreateBusStopDto busStopDto) {
    return BusStop.builder().city(busStopDto.city()).details(busStopDto.details()).build();
  }
}
