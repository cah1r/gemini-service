package dev.cah1r.geminiservice.cutomer.account;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.Ticket;
import dev.cah1r.geminiservice.TicketBundle;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("customers")
class Customer {
    @Id private String id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    @NonNull private Integer phoneNumber;
    @Setter private Address address;
    @CreatedDate private LocalDateTime createdTimestamp;
    @LastModifiedDate private LocalDateTime updatedTimestamp;
    private List<TicketBundle> activeTicketBundles;
    private List<Ticket> activeTickets;
}
