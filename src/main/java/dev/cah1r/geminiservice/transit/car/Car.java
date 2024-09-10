package dev.cah1r.geminiservice.transit.car;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name= "cars")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long id;

  @NaturalId
  @Column(nullable = false, length = 10, unique = true)
  private String registration;

  @Column(nullable = false)
  private Integer capacity;

  private Integer idNumber;

  @Column(length = 50)
  private String name;

  @CreationTimestamp
  private LocalDateTime createdTsp;
}
