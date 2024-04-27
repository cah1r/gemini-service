package dev.cah1r.geminiservice;

import dev.cah1r.geminiservice.payment.PaymentStatus;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record Ticket(
    LocalDateTime departureTime,
    String departureCity,
    String destinationCity,
    String authCode,
    BigInteger price,
    LocalDateTime dateTimeOfBuy,
    PaymentStatus paymentStatus) {}
