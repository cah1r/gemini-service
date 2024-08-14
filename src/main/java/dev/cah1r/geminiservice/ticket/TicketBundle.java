package dev.cah1r.geminiservice.ticket;

import dev.cah1r.geminiservice.ticket.dto.CreateTicketBundleDto;
import dev.cah1r.geminiservice.transit.route.Route;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("ticket_bundles")
public record TicketBundle(@Id String id, @DBRef Route route, int quantity, BigDecimal price, boolean isActive) {

  public static TicketBundle toTicketBundle(CreateTicketBundleDto dto, Route route) {
    return TicketBundle.builder()
        .price(dto.price())
        .quantity(dto.quantity())
        .route(route)
        .isActive(dto.isActive())
        .build();
  }
}
