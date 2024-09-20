package dev.cah1r.geminiservice.transit.ticket;

import dev.cah1r.geminiservice.transit.route.Route;
import dev.cah1r.geminiservice.transit.route.RouteMapper;
import dev.cah1r.geminiservice.transit.ticket.dto.CreateTicketsBundleDto;
import dev.cah1r.geminiservice.transit.ticket.dto.TicketsBundleDto;
import lombok.NoArgsConstructor;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TicketsBundleMapper {

  public static TicketsBundle toTicketsBundle(CreateTicketsBundleDto dto, Set<Route> routes) {
    return TicketsBundle.builder()
        .routes(routes)
        .ticketsQuantity(dto.ticketsQuantity())
        .price(dto.price())
        .isActive(dto.isActive())
        .build();
  }

  public static TicketsBundleDto toTicketsBundleDto(TicketsBundle ticketsBundle) {
    var routesPreview = ticketsBundle.getRoutes()
        .stream()
        .map(RouteMapper::toRouteViewDto)
        .toList();

    return new TicketsBundleDto(
        ticketsBundle.getId(),
        routesPreview,
        ticketsBundle.getTicketsQuantity(),
        ticketsBundle.getPrice(),
        ticketsBundle.getIsActive()
    );
  }
}
