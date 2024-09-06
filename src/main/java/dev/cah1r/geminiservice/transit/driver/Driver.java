package dev.cah1r.geminiservice.transit.driver;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "drivers")
@Data
public class Driver {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private Boolean isActive;

  @CreationTimestamp
  private LocalDateTime createdTsp;

  @UpdateTimestamp
  private LocalDateTime lastUpdateTsp;
}
