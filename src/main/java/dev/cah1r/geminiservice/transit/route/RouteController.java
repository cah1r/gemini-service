package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.dto.GetRouteDto;
import dev.cah1r.geminiservice.transit.dto.RouteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manage/route")
class RouteController {

  private final RouteService routeService;

  @PostMapping("/create")
  Mono<Route> createRoute(@RequestBody RouteDto routeDto) {
    return routeService.createRoute(routeDto);
  }

  @GetMapping("/get-all-eagerly")
  Flux<GetRouteDto> getAllRoutesEagerly() {
    return routeService.getAllRoutesEagerly();
  }

  @DeleteMapping("/delete/{id}")
  Mono<Void> deleteRoute(@PathVariable String id) {
    return routeService.deleteRoute(id);
  }

  @PatchMapping("/setStatus/{id}")
  Mono<Boolean> setStatus(@PathVariable String id, @RequestBody Map<String, Boolean> updatedStatus) {
    return routeService.updateStatus(id, updatedStatus.get("isActive"));
  }
}
