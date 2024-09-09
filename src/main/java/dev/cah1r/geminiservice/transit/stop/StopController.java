package dev.cah1r.geminiservice.transit.stop;

import dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopByLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/stops")
@RequiredArgsConstructor
public class StopController {

  private final StopService stopService;

  @PostMapping
  ResponseEntity<Long> createStop(@RequestBody CreateStopDto createStopDto) {
    Long stopId = stopService.createStop(createStopDto);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(stopId)
        .toUri();

    return ResponseEntity.created(location).body(stopId);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteBusStop(@PathVariable Long id) {
    stopService.deleteBusStop(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  ResponseEntity<Void> updateStops(@RequestBody List<StopByLineDto> stops) {
    stopService.updateStopsOrder(stops);
    return ResponseEntity.ok().build();
  }

}
