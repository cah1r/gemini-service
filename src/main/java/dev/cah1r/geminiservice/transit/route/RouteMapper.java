package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.dto.RouteDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class RouteMapper {

    static Route toRoute(RouteDto routeDto) {
      return Route.builder()
          .initialStopId(routeDto.initialStopId())
          .lastStopId(routeDto.lastStopId())
          .routeDurationInMinutes(routeDto.durationInMinutes())
          .priceInPennies(routeDto.ticketPriceInPennies())
          .isActive(routeDto.isActive())
          .build();
    }
}
