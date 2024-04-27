package dev.cah1r.geminiservice.payment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Document("payments")
public record Payment(
    @Id UUID id,
    BigInteger value,
    PaymentStatus paymentStatus,
    LocalDateTime startingDateTime,
    LocalDateTime lastUpdatedDateTime) {}
