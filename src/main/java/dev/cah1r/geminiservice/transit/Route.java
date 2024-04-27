package dev.cah1r.geminiservice.transit;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;

@Document("routes")
public record Route(@Id String id, BusStop stopA, BusStop stopB, Duration routeDuration) {}
