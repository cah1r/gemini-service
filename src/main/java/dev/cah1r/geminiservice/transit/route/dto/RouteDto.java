package dev.cah1r.geminiservice.transit.route.dto;

import dev.cah1r.geminiservice.transit.stop.dto.StopDto;

import java.math.BigDecimal;
import java.util.UUID;

public record RouteDto(
    UUID id,
    boolean isTicketAvailable,
    BigDecimal price,
    StopDto originStop,
    StopDto destinationStop,
    boolean isActive) {}
