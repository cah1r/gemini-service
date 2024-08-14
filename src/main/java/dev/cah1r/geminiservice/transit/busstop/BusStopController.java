package dev.cah1r.geminiservice.transit.busstop;

import dev.cah1r.geminiservice.transit.busstop.dto.BusStopDto;
import dev.cah1r.geminiservice.transit.busstop.dto.GetBusStopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/bus-stop")
@RequiredArgsConstructor
class BusStopController {

  private final BusStopService busStopService;

  @PostMapping("/create")
  Mono<BusStop> createBusStop(@RequestBody CreateBusStopDto createBusStopDto) {
    return busStopService.createBusStop(createBusStopDto);
  }

  @DeleteMapping
  Mono<Void> deleteBusStop(@RequestParam String id) {
    return busStopService.deleteBusStop(id);
  }

  @GetMapping("/get-all")
  Flux<BusStopDto> getAllBusStops() {
    return busStopService.getAllBusStops();
  }

  @PutMapping("/update")
  Mono<BusStop> updateBusStop(@RequestBody BusStopDto busStopDto) {
    return busStopService.updateBusStop(busStopDto);
  }
}
