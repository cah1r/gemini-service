package dev.cah1r.geminiservice.transit.busstop.dto;

public record GetBusStopDto(
        String id, String city, String details, boolean isTicketAvailable) {
}
