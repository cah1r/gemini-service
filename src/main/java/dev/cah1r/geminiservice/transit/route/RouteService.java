package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.error.exception.RouteNotFoundException;
import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteDto;
import dev.cah1r.geminiservice.transit.route.dto.RouteViewDto;
import dev.cah1r.geminiservice.transit.stop.Stop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

import static dev.cah1r.geminiservice.transit.route.RouteMapper.toRoute;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

  private final RouteRepository routeRepository;
  private final StopsForRouteService stopsForRouteService;


  Page<RouteDto> getAllRoutes(String keyword, int page, int size) {
    Pageable pageable = PageRequest.of(
        page, size, Sort.by("startStop.lineOrder").ascending().and(Sort.by("endStop.lineOrder").descending())
    );

    return routeRepository.getAllRoutesWithStops(keyword, pageable)
        .map(RouteMapper::toRouteDto);
  }

  public Route findRouteById(UUID id) {
    return routeRepository.findById(id).orElseThrow(() -> new RouteNotFoundException(id));
  }

  @Transactional
  public UUID createRoute(CreateRouteDto dto) {
    Map<Long, Stop> stops = stopsForRouteService.getStopsForRoute(dto);
    Route route = toRoute(dto, stops.get(dto.originStopId()), stops.get(dto.destinationStopId()));

    var routeId = routeRepository.save(route).getId();
    log.info("[{}] Route from: {} to: {} has been created",
        routeId, route.getStartStop().getTown(), route.getEndStop().getTown());

    return routeId;
  }

  @Transactional
  public void deleteRoute(UUID id) {
    routeRepository.findById(id).ifPresentOrElse(
        routeRepository::delete,
            () -> { throw new RouteNotFoundException(id); }
    );
    log.info("[{}] Route has been deleted", id);
  }

  public RouteViewDto findRouteByStopsId(Long lineId, Long originStopId, Long destinationStopId) {
    return routeRepository.findRouteByStopsIds(lineId, originStopId, destinationStopId)
        .map(RouteMapper::toRouteViewDto)
        .orElseThrow(() -> new RouteNotFoundException("Couldn't find route for given stops."));
  }
}
