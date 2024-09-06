package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.error.exception.StopNotFoundException;
import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto;
import dev.cah1r.geminiservice.transit.stop.Stop;
import dev.cah1r.geminiservice.transit.stop.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class StopsForRouteService {

  private final StopService stopService;

  Map<Long, Stop> getStopsForRoute(CreateRouteDto dto) {
    List<Long> stopsIds = List.of(dto.originStopId(), dto.destinationStopId());
    Map<Long, Stop> stops = stopService
        .findStopsByIds(stopsIds)
        .stream()
        .collect(Collectors.toMap(Stop::getId, stop -> stop));

    validateStops(stops, stopsIds);

    return stops;
  }

  private static void validateStops(Map<Long, Stop> stops, List<Long> stopsIds) {
    if (stops.size() < 2) {
      List<Long> missingIds = stopsIds.stream().filter(stops::containsKey).toList();
      throw new StopNotFoundException(missingIds);
    }
  }
}
