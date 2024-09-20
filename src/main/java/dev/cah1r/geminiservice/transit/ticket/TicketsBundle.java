package dev.cah1r.geminiservice.transit.ticket;

import dev.cah1r.geminiservice.transit.route.Route;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Builder
@Entity
@Table(name = "tickets_bundles")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TicketsBundle {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToMany(fetch = LAZY)
  @JoinTable(
      name = "tickets_bundle_route",
      joinColumns = @JoinColumn(name = "tickets_bundle_id"),
      inverseJoinColumns = @JoinColumn(name = "route_id")
  )
  private Set<Route> routes;

  @Column(nullable = false)
  private int ticketsQuantity;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private Boolean isActive;
}
