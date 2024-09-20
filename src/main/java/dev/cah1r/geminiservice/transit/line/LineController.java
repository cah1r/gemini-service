package dev.cah1r.geminiservice.transit.line;

import dev.cah1r.geminiservice.transit.line.dto.CreateLineDto;
import dev.cah1r.geminiservice.transit.line.dto.LineDto;
import dev.cah1r.geminiservice.transit.line.dto.LineViewDto;
import dev.cah1r.geminiservice.transit.stop.StopService;
import dev.cah1r.geminiservice.transit.stop.dto.StopByLineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/lines")
@RequiredArgsConstructor
class LineController {

  private final LineService lineService;
  private final StopService stopService;

  @PostMapping
  ResponseEntity<LineDto> createNewLine(@RequestBody CreateLineDto lineDto) {
    return ResponseEntity.ok(lineService.createNewLine(lineDto));
  }

  @GetMapping
  List<LineViewDto> getAllLines() {
    return lineService.getAllLines();
  }

  @GetMapping("/{lineId}/stops")
  List<StopByLineDto> getLineStops(@PathVariable Long lineId) {
    return stopService.getAllBusStopsByLine(lineId);
  }
}
