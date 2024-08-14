package dev.cah1r.geminiservice.ticket;

import dev.cah1r.geminiservice.payment.PaymentStatus;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tickets")
public record Ticket(
    @Id String id,
    LocalDateTime departureTime,
    String departureStopId,
    String destinationStopId,
    String authCode,
    Integer priceInPennies,
    LocalDateTime dateTimeOfBuy,
    PaymentStatus paymentStatus,
    String userId) {}
