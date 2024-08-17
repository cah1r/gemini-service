package dev.cah1r.geminiservice.transit.stop.dto;

import java.time.LocalTime;
import java.util.List;

public record StopWithSchedulesDto(Long id, String town, String details, Integer lineOrder, List<LocalTime> departures) {}
