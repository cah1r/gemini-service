package dev.cah1r.geminiservice.transit;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("bus_stops")
public record BusStop(@Id String id, String city, String details) {}
