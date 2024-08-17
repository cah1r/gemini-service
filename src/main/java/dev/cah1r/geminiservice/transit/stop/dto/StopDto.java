package dev.cah1r.geminiservice.transit.stop.dto;

import lombok.Builder;

@Builder
public record StopDto(Long id, String town, String details, Integer lineOrder) {
}
