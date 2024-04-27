package dev.cah1r.geminiservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TicketBundleRepository extends ReactiveMongoRepository<TicketBundle, String> {}
