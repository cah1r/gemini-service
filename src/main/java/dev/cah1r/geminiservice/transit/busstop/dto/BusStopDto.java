package dev.cah1r.geminiservice.transit.busstop.dto;

import dev.cah1r.geminiservice.transit.Line;

import java.util.List;

public record BusStopDto(String id, String city, String details, boolean isTicketAvailable, List<Line> lines) {}
