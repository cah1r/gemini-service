package dev.cah1r.geminiservice.transit;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface LineRepository extends ReactiveMongoRepository<Line, String> {
    Mono<Boolean> existsByName(String name);
}
