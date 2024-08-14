package dev.cah1r.geminiservice.transit.dto;

import dev.cah1r.geminiservice.transit.Line;

public record CreateLineDto(String name) {

    public static Line toLine(CreateLineDto request) {
        return Line.builder().name(request.name()).build();
    }
}
