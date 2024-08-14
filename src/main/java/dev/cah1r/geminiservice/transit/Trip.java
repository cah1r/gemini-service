package dev.cah1r.geminiservice.transit;

import java.time.LocalDateTime;

import dev.cah1r.geminiservice.transit.route.Route;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("trips")
public record Trip(
    @Id String id,
    LocalDateTime startDateTime,
    LocalDateTime arrivalDateTime,
    Route route,
    int totalTickets,
    int availableTickets) {}
