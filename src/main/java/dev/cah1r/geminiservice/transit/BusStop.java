package dev.cah1r.geminiservice.transit;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("bus_stops")
public class BusStop {
  @Id private String id;
  private String city;
  private String details;
}
