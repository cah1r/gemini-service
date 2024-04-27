package dev.cah1r.geminiservice.transit;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record Trip(
       LocalDateTime startDateTime,
       Route route,
       int totalTickets,
       int availableTickets,
       BigInteger price
) {}
