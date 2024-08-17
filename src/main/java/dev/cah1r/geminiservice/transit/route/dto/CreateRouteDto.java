package dev.cah1r.geminiservice.transit.route.dto;

import java.math.BigDecimal;

public record CreateRouteDto(
    Long originStopId,
    Long destinationStopId,
    BigDecimal price,
    boolean isTicketAvailable,
    boolean isActive) {}
