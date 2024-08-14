package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.error.exception.CreateRouteException;
import dev.cah1r.geminiservice.transit.busstop.BusStop;
import dev.cah1r.geminiservice.transit.busstop.BusStopService;
import dev.cah1r.geminiservice.transit.dto.GetRouteDto;
import dev.cah1r.geminiservice.transit.dto.RouteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

  private final RouteRepository routeRepository;
  private final RouteTemplateRepository routeTemplateRepository;
  private final BusStopService busStopService;

  Mono<Route> createRoute(RouteDto routeDto) {
    Mono<BusStop> initialStop = busStopService.findBusStopById(routeDto.initialStopId());
    Mono<BusStop> lastStop = busStopService.findBusStopById(routeDto.lastStopId());

    return Mono
            .zip(initialStop, lastStop)
            .doOnNext(e -> log.info("Found bust stops in {} and {}", e.getT1().getCity(), e.getT2().getCity()))
            .then(saveRouteInDb(RouteMapper.toRoute(routeDto)));
  }

  private Mono<Route> saveRouteInDb(Route route) {
    return routeRepository
        .save(route)
        .doOnNext(entity -> log.info("Created new route with id: {}", entity.getId()))
        .doOnError(CreateRouteException::new);
  }

  public Mono<Route> findRoute(String initialStopId, String finalStopId) {
    return routeRepository.findRouteByInitialStopIdAndLastStopId(initialStopId, finalStopId);
  }

  Flux<GetRouteDto> getAllRoutesEagerly() {
    return routeTemplateRepository.fetchAllRoutesEagerly();

//    return routeRepository
//            .findAll()
//            .collectList()
//            .flatMapMany(this::completeRoutesWithBusStops);
  }

//  private Flux<GetRouteDto> completeRoutesWithBusStops(List<Route> routes) {
//    Set<String> busStopsIds = routes.stream()
//            .flatMap(route -> Stream.of(route.getInitialStopId(), route.getLastStopId()))
//            .collect(Collectors.toSet());
//
//    return busStopService
//            .findAllById(busStopsIds)
//            .collectMap(BusStop::getId)
//            .flatMapMany(busStopMap -> Flux.fromIterable(routes)
//                    .map(route -> RouteMapper.toGetRouteDto(
//                            route,
//                            busStopMap.get(route.getInitialStopId()),
//                            busStopMap.get(route.getLastStopId())
//                    )));
//  }

  Mono<Void> deleteRoute(String id) {
    return routeRepository.deleteById(id)
            .doOnSuccess(data -> log.info("Successfully deleted route with id: {}", id))
            .doOnError(t -> log.error("Error deleting route with id: {}", id, t));
  }

  Mono<Boolean> updateStatus(String id, Boolean isActive) {
    return routeRepository.findById(id).flatMap(route -> {
      route.setActive(isActive);
      return routeRepository.save(route)
              .map(Route::isActive);
    });
  }
}
