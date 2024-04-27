package dev.cah1r.geminiservice.cutomer.account;

import dev.cah1r.geminiservice.Ticket;
import dev.cah1r.geminiservice.TicketBundle;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("customers")
record Customer(
    @Id String id,
    String firstName,
    String lastName,
    String email,
    int phoneNumber,
    Address address,
    @CreatedDate LocalDateTime createdTimestamp,
    @LastModifiedDate LocalDateTime updatedTimestamp,
    List<TicketBundle> activeTicketBundles,
    List<Ticket> activeTickets) {}
