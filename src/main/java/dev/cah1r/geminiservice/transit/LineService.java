package dev.cah1r.geminiservice.transit;

import dev.cah1r.geminiservice.error.exception.LineAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class LineService {

  private final LineRepository lineRepository;

  @Transactional
  public LineDto createNewLine(CreateLineDto lineDto) {
    if (lineRepository.existsByDescription(lineDto.description())) {
      throw new LineAlreadyExistsException(lineDto.description());
    }
    var line = lineRepository.save(Line.builder().description(lineDto.description()).build());
    log.info("New Line created with description: {} and id: {}", line.getDescription(), line.getId());

    return new LineDto(line.getId(), line.getDescription(), List.of());
  }

  List<LineDto> getAllLines() {
    return lineRepository.findAllWithStopsAndSchedules()
        .stream()
        .map(LineMapper::toLineDto)
        .toList();
  }
}
