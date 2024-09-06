package dev.cah1r.geminiservice.transit.line.dto;

import dev.cah1r.geminiservice.transit.stop.dto.StopWithSchedulesDto;

import java.util.List;

public record LineDto(Long id, String description, List<StopWithSchedulesDto> stops) {}
