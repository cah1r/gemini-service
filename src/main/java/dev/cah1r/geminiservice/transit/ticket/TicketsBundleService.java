package dev.cah1r.geminiservice.transit.ticket;

import dev.cah1r.geminiservice.error.exception.TicketsBundleNotFoundException;
import dev.cah1r.geminiservice.transit.route.Route;
import dev.cah1r.geminiservice.transit.route.RouteService;
import dev.cah1r.geminiservice.transit.ticket.dto.CreateTicketsBundleDto;
import dev.cah1r.geminiservice.transit.ticket.dto.TicketsBundleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketsBundleService {

  private final TicketsBundleRepository ticketsBundleRepository;
  private final RouteService routeService;

  List<TicketsBundleDto> getAllTicketsBundles() {
    return ticketsBundleRepository.getAllTicketsBundlesWithRoutesAndStops()
        .stream()
        .map(TicketsBundleMapper::toTicketsBundleDto)
        .toList();
  }

  @Transactional
  public TicketsBundleDto createTicketsBundle(CreateTicketsBundleDto dto) {
    Set<Route> routes = findRoutesForTicketsBundle(dto);
    TicketsBundle savedBundle = ticketsBundleRepository.save(TicketsBundleMapper.toTicketsBundle(dto, routes));

    return TicketsBundleMapper.toTicketsBundleDto(savedBundle);
  }

  private Set<Route> findRoutesForTicketsBundle(CreateTicketsBundleDto dto) {
    return dto.routesIds()
        .stream()
        .map(routeService::findRouteById)
        .collect(Collectors.toSet());
  }

  @Transactional
  public void deleteTicketsBundleById(UUID id) {
    ticketsBundleRepository.findById(id)
            .ifPresentOrElse(
                ticketsBundleRepository::delete,
                () -> { throw new TicketsBundleNotFoundException(id); }
            );
  }
}
