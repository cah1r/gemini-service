package dev.cah1r.geminiservice.transit;

import dev.cah1r.geminiservice.error.exception.LineAlreadyExistsException;
import dev.cah1r.geminiservice.transit.dto.CreateLineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.Boolean.FALSE;

@Service
@RequiredArgsConstructor
public class LineService {
    
    private final LineRepository lineRepository;

    Mono<Line> saveNewLine(CreateLineDto request) {
    return lineRepository
        .existsByName(request.name())
        .flatMap(result -> FALSE.equals(result)
                    ? lineRepository.save(CreateLineDto.toLine(request))
                    : Mono.error(new LineAlreadyExistsException(request.name())));
    }

    Flux<Line> getAllLines() {
        return lineRepository.findAll();
    }

    public Mono<Line> findLineById(String id) {
        return lineRepository.findById(id);
    }
}
