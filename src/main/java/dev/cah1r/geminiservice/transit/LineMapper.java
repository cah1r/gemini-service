package dev.cah1r.geminiservice.transit;

import dev.cah1r.geminiservice.transit.stop.StopMapper;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class LineMapper {

  static LineDto toLineDto(Line line) {
    return new LineDto(
        line.getId(),
        line.getDescription(),
        Optional.ofNullable(line.getLineStops())
            .map(stops -> stops.stream().map(StopMapper::toStopWithSchedulesDto).toList())
            .orElse(List.of())
    );
  }
}
