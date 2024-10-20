package dev.cah1r.geminiservice.transit.line;

import dev.cah1r.geminiservice.transit.stop.Stop;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "lines")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "description")
@ToString
public class Line {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @NaturalId
  @Column(length = 50, nullable = false)
  private String description;

  @ToString.Exclude
  @OneToMany(
      fetch = LAZY,
      mappedBy = "line",
      cascade = ALL,
      orphanRemoval = true
  )
  private Set<Stop> lineStops = new HashSet<>();

}