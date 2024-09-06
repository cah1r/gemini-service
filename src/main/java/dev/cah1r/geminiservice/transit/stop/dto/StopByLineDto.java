package dev.cah1r.geminiservice.transit.stop.dto;

public record StopByLineDto(Long id, String town, String details, Integer lineOrder, Long lineId) {
}
