package dev.cah1r.geminiservice.transit;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BusStopRepository extends ReactiveMongoRepository<BusStop, String> {
  Mono<BusStop> findFirstByCityAndDetails(String city, String details);
}
