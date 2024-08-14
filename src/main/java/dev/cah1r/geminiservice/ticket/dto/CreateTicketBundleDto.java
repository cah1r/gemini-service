package dev.cah1r.geminiservice.ticket.dto;

import java.math.BigDecimal;

public record CreateTicketBundleDto(
    int quantity, BigDecimal price, String initialCity, String finalStop, boolean isActive) {}
