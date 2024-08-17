package dev.cah1r.geminiservice.transit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/line")
@RequiredArgsConstructor
class LineController {

  private final LineService lineService;

  @PostMapping("/create")
  ResponseEntity<LineDto> createNewLine(@RequestBody CreateLineDto lineDto) {
    return ResponseEntity.ok(lineService.createNewLine(lineDto));
  }

  @GetMapping("/get-all")
  List<LineDto> getAllLines() {
    return lineService.getAllLines();
  }
}
