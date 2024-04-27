package dev.cah1r.geminiservice.payment;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface PaymentRepository extends ReactiveMongoRepository<Payment, UUID> {}
