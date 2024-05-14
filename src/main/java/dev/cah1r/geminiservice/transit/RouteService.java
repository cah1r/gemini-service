package dev.cah1r.geminiservice.transit;

import dev.cah1r.geminiservice.error.exception.CreateRouteException;
import dev.cah1r.geminiservice.transit.dto.RouteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static dev.cah1r.geminiservice.transit.dto.RouteDto.toRoute;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

  private final RouteRepository routeRepository;

  Mono<Route> createRoute(RouteDto routeDto) {
    return routeRepository
        .save(toRoute(routeDto))
        .doOnNext(entity -> log.info("Created new route with id: {}. {} <-> {}",
                    entity.id(),
                    entity.initialStop().getCity(),
                    entity.lastStop().getCity()))
        .doOnError(err -> {
              log.error("Failed saving route in db with exception:", err);
              throw new CreateRouteException(err);
            });
  }
}
