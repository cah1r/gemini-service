package dev.cah1r.geminiservice.transit.busstop;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface BusStopRepository extends ReactiveMongoRepository<BusStop, String> {
  Mono<Boolean> existsByCityAndDetailsAndLines_Id(String city, String details, String lineId);
}
