package dev.cah1r.geminiservice;

import dev.cah1r.geminiservice.transit.Route;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document("ticket_bundles")
public record TicketBundle(@Id String id, Route route, int quantity, BigInteger price) {}
