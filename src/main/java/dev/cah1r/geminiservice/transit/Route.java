package dev.cah1r.geminiservice.transit;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.Duration;

@Builder
@Document("routes")
public record Route(
    @Id String id,
    BusStop initialStop,
    BusStop lastStop,
    Duration routeDuration,
    BigInteger price) {}
