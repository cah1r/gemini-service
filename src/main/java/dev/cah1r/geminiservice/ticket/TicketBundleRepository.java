package dev.cah1r.geminiservice.ticket;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

interface TicketBundleRepository extends ReactiveMongoRepository<TicketBundle, String> {

    Flux<TicketBundle> findByIsActive(boolean isActive);
}
