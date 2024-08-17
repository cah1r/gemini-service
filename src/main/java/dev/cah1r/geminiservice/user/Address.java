package dev.cah1r.geminiservice.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="addresses")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, length = 150)
    private String street;

    @Column(nullable = false, length = 6)
    private String buildingNo;

    @Column(nullable = false, length = 6)
    private String apartmentNo;

    @Column(nullable = false, length = 70)
    private String city;

    @Column(nullable = false, length = 6)
    private String zipCode;

    @Column(nullable = false, length = 16)
    private String nip;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    @ToString.Exclude
    private User user;

}