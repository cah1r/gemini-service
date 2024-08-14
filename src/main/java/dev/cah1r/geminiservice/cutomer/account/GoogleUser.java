package dev.cah1r.geminiservice.cutomer.account;

import com.mongodb.lang.NonNull;
import dev.cah1r.geminiservice.ticket.Ticket;
import dev.cah1r.geminiservice.ticket.TicketBundle;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("users_google")
public class GoogleUser {
    @Id private String id;
    @NonNull private String email;
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;
    private List<String> ticketBundleIds;
    private List<String> ticketsIds;
    private String addressId;
}
