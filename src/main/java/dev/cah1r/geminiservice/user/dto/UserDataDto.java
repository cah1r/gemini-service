package dev.cah1r.geminiservice.user.dto;

import dev.cah1r.geminiservice.transit.ticket.Ticket;
import dev.cah1r.geminiservice.transit.ticket.TicketsBundle;
import dev.cah1r.geminiservice.user.Address;
import dev.cah1r.geminiservice.user.Role;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserDataDto(
    UUID id,
    String firstName,
    String lastName,
    String email,
    Integer phoneNumber,
    Role role,
    Address address,
    List<TicketsBundle> activeTicketsBundles,
    List<Ticket> activeTickets
) {}
