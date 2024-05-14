package dev.cah1r.geminiservice.transit;

import dev.cah1r.geminiservice.transit.dto.RouteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/tools/route")
public class RouteController {

  private final RouteService routeService;

  @PostMapping
  Mono<Route> createRoute(@RequestBody RouteDto routeDto) {
    return routeService.createRoute(routeDto);
  }
}
