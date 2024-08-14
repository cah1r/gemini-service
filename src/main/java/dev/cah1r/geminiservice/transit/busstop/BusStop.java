package dev.cah1r.geminiservice.transit.busstop;

import dev.cah1r.geminiservice.transit.Line;
import dev.cah1r.geminiservice.transit.busstop.dto.BusStopDto;
import dev.cah1r.geminiservice.transit.busstop.dto.GetBusStopDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document("bus_stops")
public class BusStop {
    @Id
    private String id;
    private String city;
    private String details;
    private boolean isTicketAvailable;
    private List<Line> lines;

    static BusStopDto toBusStopDto(BusStop entity) {
        return new BusStopDto(
                entity.getId(),
                entity.getCity(),
                entity.getDetails(),
                entity.isTicketAvailable(),
                entity.getLines()
        );
    }
}
