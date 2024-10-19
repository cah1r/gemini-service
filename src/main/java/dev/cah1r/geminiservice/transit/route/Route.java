package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.stop.Stop;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "routes")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Route {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private boolean isTicketAvailable;

  private BigDecimal price;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "start_stop_id", nullable = false)
  @ToString.Exclude
  private Stop startStop;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "end_stop_id", nullable = false)
  @ToString.Exclude
  private Stop endStop;

  @Column(nullable = false)
  private boolean isActive;

  @Column(nullable = false)
  private Long lineId;
}
