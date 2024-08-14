package dev.cah1r.geminiservice.transit.route;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("routes")
@Getter
@EqualsAndHashCode
public class Route {
    @Id private String id;
    private String initialStopId;
    private String lastStopId;
    private String lineId;
    private Integer routeDurationInMinutes;
    private Integer priceInPennies;
    @Setter private boolean isActive;
}
