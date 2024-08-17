package dev.cah1r.geminiservice.transit.ticket;

import dev.cah1r.geminiservice.payment.Payment;
import dev.cah1r.geminiservice.payment.PaymentStatus;
import dev.cah1r.geminiservice.transit.route.Route;
import dev.cah1r.geminiservice.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    @ToString.Exclude
    private Route route;

    @Column(unique = true, length = 6)
    private String authCode;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "ticket")
    @ToString.Exclude
    private List<Payment> payments;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    private LocalDateTime purchaseTsp;

}

