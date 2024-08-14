package dev.cah1r.geminiservice.cutomer.account;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.ticket.Ticket;
import dev.cah1r.geminiservice.ticket.TicketBundle;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("customers")
class Customer {
  @Id private String id;
  @NonNull private String email;
  @NonNull private String password;
  private String firstName;
  private String lastName;
  private Integer phoneNumber;
  @Setter private Address address;
  private LocalDateTime createdTimestamp;
  private LocalDateTime updatedTimestamp;
  @DBRef private List<TicketBundle> ticketBundles;
  @DBRef private List<Ticket> tickets;
}
