package dev.cah1r.geminiservice.transit.ticket;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tickets_bundles")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TicketsBundle {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID routeId;

  @Column(nullable = false)
  private int ticketsQuantity;

  @Column(nullable = false)
  private BigDecimal price;
}
