package dev.cah1r.geminiservice.transit.ticket.dto;

import dev.cah1r.geminiservice.transit.route.dto.RouteViewDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TicketsBundleDto(
    UUID id,
    List<RouteViewDto> routesPreview,
    Integer ticketsQuantity,
    BigDecimal price,
    Boolean isActive
) {}
