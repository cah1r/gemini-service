package dev.cah1r.geminiservice.cutomer.account;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

  Mono<Boolean> existsByEmail(String email);

  Mono<Customer> findCustomerByEmail(String email);
  
}
