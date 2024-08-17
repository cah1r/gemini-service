package dev.cah1r.geminiservice.transit.stop;

import dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopWithSchedulesDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopWithLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/stop")
@RequiredArgsConstructor
public class StopController {

  private final StopService stopService;

  @PostMapping("/create")
  Stop createStop(@RequestBody CreateStopDto createStopDto) {
    return stopService.createStop(createStopDto);
  }

  @DeleteMapping("/delete/{id}")
  ResponseEntity<Void> deleteBusStop(@PathVariable Long id) {
    stopService.deleteBusStop(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/get-all")
  List<StopWithLineDto> getAllBusStops() {
    return stopService.getAllBusStops();
  }

  @PutMapping("/update-order")
  ResponseEntity<Void> updateStops(@RequestBody Set<StopWithSchedulesDto> stops) {
    stopService.updateStopsOrder(stops);
    return ResponseEntity.ok().build();
  }

}
