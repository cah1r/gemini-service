package dev.cah1r.geminiservice.transit.stop.dto;

import dev.cah1r.geminiservice.transit.line.Line;
import dev.cah1r.geminiservice.transit.stop.Stop;

public record CreateStopDto(String town, String details, Line line, Integer lineOrder) {

  public static Stop toBusStop(CreateStopDto dto) {
    return Stop.builder()
        .town(dto.town())
        .details(dto.details())
        .line(dto.line)
        .lineOrder(dto.lineOrder())
        .build();
  }
}
