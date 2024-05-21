package dev.cah1r.geminiservice.transit;

import dev.cah1r.geminiservice.transit.dto.CreateBusStopDto;
import dev.cah1r.geminiservice.transit.dto.BusStopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/bus-stop")
@RequiredArgsConstructor
public class BusStopController {

  private final BusStopService busStopService;

  @PostMapping
  Mono<BusStopDto> createBusStop(@RequestBody CreateBusStopDto createBusStopDto) {
    return busStopService.createBusStop(createBusStopDto);
  }

  @DeleteMapping
  Mono<Void> deleteBusStop(@RequestParam String id) {
    return busStopService.deleteBusStop(id);
  }

  @GetMapping("/getAll")
  Flux<BusStopDto> getAllBusStops() {
    return busStopService.getAllBusStops();
  }

  @PutMapping("/update")
  Mono<BusStopDto> updateBusStop(@RequestBody BusStopDto busStopDto) {
    return busStopService.updateBusStop(busStopDto);
  }
}
