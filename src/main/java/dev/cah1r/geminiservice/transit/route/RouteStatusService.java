package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.error.exception.RouteNotFoundException;
import dev.cah1r.geminiservice.transit.route.dto.RouteStatusDto;
import dev.cah1r.geminiservice.transit.route.dto.TicketAvailabilityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class RouteStatusService {

  private final RouteRepository routeRepository;

  @Transactional
  public boolean setStatus(UUID id, RouteStatusDto dto) {
    return routeRepository.findById(id)
        .map(route -> setStatusAndSaveInDb(dto, route))
        .map(Route::isActive)
        .orElseThrow(() -> new RouteNotFoundException(dto.id()));
  }

  private Route setStatusAndSaveInDb(RouteStatusDto dto, Route route) {
    route.setActive(dto.isActive());
    return routeRepository.save(route);
  }

  @Transactional
  public boolean setTicketAvailability(UUID id, TicketAvailabilityDto dto) {
    return routeRepository.findById(id)
        .map(route -> setStatusAndSave(dto, route))
        .map(Route::isTicketAvailable)
        .orElseThrow(() -> new RouteNotFoundException(id));
  }

  private Route setStatusAndSave(TicketAvailabilityDto dto, Route route) {
    route.setTicketAvailable(dto.isTicketAvailable());
    return routeRepository.save(route);
  }
}
