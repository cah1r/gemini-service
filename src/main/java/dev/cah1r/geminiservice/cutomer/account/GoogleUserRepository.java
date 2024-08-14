package dev.cah1r.geminiservice.cutomer.account;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface GoogleUserRepository extends ReactiveMongoRepository<GoogleUser, String> {

    Mono<GoogleUser> findGoogleUserByEmail(String email);
}
