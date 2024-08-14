package dev.cah1r.geminiservice.transit.dto;

import com.mongodb.lang.NonNull;

public record RouteDto(
    @NonNull String initialStopId,
    @NonNull String lastStopId,
    Long lineId,
    Integer durationInMinutes,
    Integer ticketPriceInPennies,
    boolean isActive) {
}
