package dev.cah1r.geminiservice.transit;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BusStopRepository extends ReactiveMongoRepository<BusStop, String> {}
