package dev.cah1r.geminiservice.transit.stop;

import dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopByLineDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/stops")
@RequiredArgsConstructor
public class StopController {

  private final StopService stopService;

  @PostMapping
  Stop createStop(@RequestBody CreateStopDto createStopDto) {
    return stopService.createStop(createStopDto);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteBusStop(@PathVariable Long id) {
    stopService.deleteBusStop(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  List<StopDto> getAllBusStops() {
    return stopService.getAllBusStops();
  }

  @PutMapping
  ResponseEntity<Void> updateStops(@RequestBody List<StopByLineDto> stops) {
    stopService.updateStopsOrder(stops);
    return ResponseEntity.ok().build();
  }

}
