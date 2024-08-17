package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.error.exception.BusStopNotFoundException;
import dev.cah1r.geminiservice.error.exception.RouteNotFoundException;
import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteStatusDto;
import dev.cah1r.geminiservice.transit.route.dto.TicketAvailabilityDto;
import dev.cah1r.geminiservice.transit.stop.StopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

  private final RouteRepository routeRepository;
  private final StopService stopService;


  Page<RouteDto> getAllRoutes(String keyword, int page, int size) {
    Pageable pageable = PageRequest.of(
        page, size, Sort.by("startStop.lineOrder").ascending().and(Sort.by("endStop.lineOrder").descending())
    );

    return routeRepository.getAllRoutesWithStops(keyword, pageable)
        .map(RouteMapper::toRouteDto);
  }

  @Transactional
  public UUID createRoute(CreateRouteDto createRouteDto) {
    var startStop = stopService
        .findStopById(createRouteDto.originStopId())
        .orElseThrow(() -> new BusStopNotFoundException(createRouteDto.originStopId()));

    var endStop = stopService
        .findStopById(createRouteDto.destinationStopId())
        .orElseThrow(() -> new BusStopNotFoundException(createRouteDto.destinationStopId()));

    return routeRepository.save(RouteMapper.toRoute(createRouteDto, startStop, endStop)).getId();
  }

  @Transactional
  public Boolean setStatus(RouteStatusDto dto) {
    return routeRepository.findById(dto.id())
        .map(route -> setStatusAndSaveInDb(dto, route))
        .map(Route::isActive)
        .orElseThrow(() -> new RouteNotFoundException(dto.id()));
  }

  private Route setStatusAndSaveInDb(RouteStatusDto dto, Route route) {
    route.setActive(dto.isActive());
    return routeRepository.save(route);
  }

  @Transactional
  public void deleteRoute(UUID id) {
    routeRepository.deleteById(id);
  }

  @Transactional
  public boolean setTicketAvailability(UUID id, TicketAvailabilityDto dto) {
    return routeRepository.findById(id)
        .map(route -> setStatusAndSave(dto.isTicketAvailable(), route))
        .map(Route::isTicketAvailable)
        .orElseThrow(() -> new RouteNotFoundException(id));
  }

  private Route setStatusAndSave(boolean ticketAvailability, Route route) {
    route.setTicketAvailable(ticketAvailability);
    return routeRepository.save(route);
  }
}
