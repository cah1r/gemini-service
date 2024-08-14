package dev.cah1r.geminiservice.transit;

import dev.cah1r.geminiservice.transit.dto.CreateLineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/manage/lines")
@RestController
@RequiredArgsConstructor
class LineController {

    private final LineService lineService;

    @GetMapping("/get-all")
    Flux<Line> getAllLines() {
        return lineService.getAllLines().log();
    }

    @PostMapping("/create")
    Mono<Line> createNewLine(@RequestBody CreateLineDto request) {
        return lineService.saveNewLine(request);
    }

}
