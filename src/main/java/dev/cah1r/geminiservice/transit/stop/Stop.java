package dev.cah1r.geminiservice.transit.stop;

import dev.cah1r.geminiservice.transit.line.Line;
import dev.cah1r.geminiservice.transit.route.Route;
import dev.cah1r.geminiservice.transit.route.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name="stops")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"startRoutes", "endRoutes", "schedules", "line"})
@NoArgsConstructor
@AllArgsConstructor
public class Stop {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @Column(length = 70, nullable = false)
  private String town;

  @Column(length = 70)
  private String details;

  @OneToMany(mappedBy = "startStop", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private List<Route> startRoutes;

  @OneToMany(mappedBy = "endStop", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private List<Route> endRoutes;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "line_id", nullable = false)
  private Line line;

  @Column(nullable = false)
  private Integer lineOrder;

  @OneToMany(mappedBy = "stop", cascade = ALL, orphanRemoval = true)
  @ToString.Exclude
  private Set<Schedule> schedules;
}
