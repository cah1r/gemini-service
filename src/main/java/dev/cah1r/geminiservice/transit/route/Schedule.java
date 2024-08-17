package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.stop.Stop;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = "stop")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Schedule {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "stop_id", nullable = false)
  @ToString.Exclude
  private Stop stop;

  @Column(nullable = false)
  private LocalTime departureTime;

  @Column(nullable = false)
  private Integer courseNo;
}
