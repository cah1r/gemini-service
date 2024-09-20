package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteViewDto;
import dev.cah1r.geminiservice.transit.stop.Stop;
import dev.cah1r.geminiservice.transit.stop.StopMapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RouteMapper {

  public static RouteDto toRouteDto(Route route) {
    return new RouteDto(
        route.getId(),
        route.isTicketAvailable(),
        route.getPrice(),
        StopMapper.toStopDto(route.getStartStop()),
        StopMapper.toStopDto(route.getEndStop()),
        route.isActive()
    );
  }

  public static Route toRoute(CreateRouteDto createRouteDto, Stop startStop, Stop endStop) {
    return Route.builder()
        .price(createRouteDto.price())
        .startStop(startStop)
        .endStop(endStop)
        .isActive(createRouteDto.isActive())
        .isTicketAvailable(createRouteDto.isTicketAvailable())
        .build();
  }

  public static RouteViewDto toRouteViewDto(Route route) {
    return new RouteViewDto(
        route.getId(),
        route.getStartStop().getTown(),
        route.getStartStop().getDetails(),
        route.getEndStop().getTown(),
        route.getEndStop().getDetails()
    );
  }
}
