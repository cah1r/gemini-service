package dev.cah1r.geminiservice.transit.dto;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.transit.BusStop;
import dev.cah1r.geminiservice.transit.Route;

import java.math.BigInteger;
import java.time.Duration;

public record RouteDto(
    @NonNull BusStop departureBusStop,
    @NonNull BusStop arrivalBusStop,
    Duration travelDuration,
    BigInteger ticketPrice) {

  public static Route toRoute(RouteDto routeDto) {
    return Route.builder()
        .initialStop(routeDto.departureBusStop)
        .lastStop(routeDto.arrivalBusStop)
        .routeDuration(routeDto.travelDuration)
        .price(routeDto.ticketPrice)
        .build();
  }
}
