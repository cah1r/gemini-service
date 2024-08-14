package dev.cah1r.geminiservice.transit.route;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface RouteRepository extends ReactiveMongoRepository<Route, String> {

    Mono<Route> findRouteByInitialStopIdAndLastStopId(String initialStopId, String lastStopId);
}
