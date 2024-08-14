package dev.cah1r.geminiservice.transit.route;

import dev.cah1r.geminiservice.transit.dto.GetRouteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class RouteTemplateRepository {

    private final ReactiveMongoTemplate template;

    public Flux<GetRouteDto> fetchAllRoutesEagerly() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("bus_stops", "initialStopId", "_id", "initialStop"),
                Aggregation.lookup("bus_stops", "lastStopId", "_id", "lastStopId"),
                Aggregation.lookup("lines", "lineId", "_id", "line"),
                Aggregation.unwind("initialStop", true),
                Aggregation.unwind("lastStop", true),
                Aggregation.unwind("line", true),
                Aggregation.project("id", "routeDurationInMinutes", "priceInPennies", "isActive")
                        .and("initialStop").as("initialStop")
                        .and("lastStop").as("lastStop")
                        .and("line").as("line")
        );

        return template.aggregate(aggregation, "routes", GetRouteDto.class);
    }
}
