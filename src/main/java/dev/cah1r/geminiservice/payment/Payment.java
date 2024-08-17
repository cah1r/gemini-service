package dev.cah1r.geminiservice.payment;

import dev.cah1r.geminiservice.transit.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "payments")
@Data
@EqualsAndHashCode(of = "id")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "ticket_id", referencedColumnName = "id")
  private Ticket ticket;

  @Column(nullable = false)
  private BigDecimal value;

  @Enumerated(STRING)
  @Column(nullable = false)
  private PaymentStatus paymentStatus;

  @CreationTimestamp
  private LocalDateTime createdTsp;

  @UpdateTimestamp
  private LocalDateTime updatedTsp;

}
