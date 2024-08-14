package dev.cah1r.geminiservice.cutomer.account.dto;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.ticket.Ticket;
import dev.cah1r.geminiservice.ticket.TicketBundle;
import dev.cah1r.geminiservice.cutomer.account.Address;
import lombok.Builder;

import java.util.List;

@Builder
public record CustomerDataDto(
    @NonNull String id,
    String firstName,
    String lastName,
    String email,
    Integer phoneNumber,
    Address address,
    List<TicketBundle> activeTicketBundles,
    List<Ticket> activeTickets) {}
