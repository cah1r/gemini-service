package dev.cah1r.geminiservice.transit.ticket.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateTicketsBundleDto(
    Long stopAId,
    Long stopBId,
    List<UUID> routesIds,
    Integer ticketsQuantity,
    BigDecimal price,
    boolean isActive) {}