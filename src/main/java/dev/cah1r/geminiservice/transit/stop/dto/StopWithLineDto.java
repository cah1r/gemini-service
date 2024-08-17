package dev.cah1r.geminiservice.transit.stop.dto;

import lombok.Builder;

@Builder
public record StopWithLineDto(Long id, String town, String details, Integer lineOrder, Long lineId) {
}
