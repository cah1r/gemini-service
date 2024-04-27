package dev.cah1r.geminiservice.cutomer.account;

import static dev.cah1r.geminiservice.cutomer.account.CustomerMapper.toCustomer;
import static java.util.Optional.ofNullable;

import dev.cah1r.geminiservice.error.exception.CustomerAlreadyExistsException;
import dev.cah1r.geminiservice.error.exception.CustomerNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
class CustomerService {

  private final CustomerRepository customerRepository;
  private final ReactiveMongoTemplate reactiveMongoTemplate;

  Mono<Customer> createCustomer(CustomerDataDto customerDataDto) {
    return customerRepository
        .existsByEmail(customerDataDto.email())
        .flatMap(
            exists ->
                exists
                    ? Mono.error(new CustomerAlreadyExistsException(customerDataDto.email()))
                    : saveCustomerInDb(customerDataDto));
  }

  private Mono<Customer> saveCustomerInDb(CustomerDataDto customerDataDto) {
    return customerRepository
        .save(toCustomer(customerDataDto))
        .doOnNext(
            customer -> log.info("Saved customer in database with email: {}", customer.email()))
        .doOnError(
            err -> log.error("Could not save in database customer: {}", customerDataDto, err));
  }

  Mono<Customer> updateCustomer(String currentEmail, CustomerDataDto customerDataDto) {
    return customerRepository
        .existsByEmail(currentEmail)
        .flatMap(
            exists ->
                exists
                    ? updateAndSaveInDb(currentEmail, customerDataDto)
                    : Mono.error(new CustomerNotFoundException(currentEmail)));
  }

  Mono<Customer> updateAndSaveInDb(String currentEmail, CustomerDataDto customerDataDto) {
    Query query = new Query().addCriteria(Criteria.where("email").is(currentEmail));
    Update updateDefinition = new Update().set("updatedTimestamp", LocalDateTime.now());

    ofNullable(customerDataDto.firstName()).ifPresent(firstName -> updateDefinition.set("firstName", firstName));
    ofNullable(customerDataDto.lastName()).ifPresent(lastName -> updateDefinition.set("lastName", lastName));
    ofNullable(customerDataDto.email()).ifPresent(email -> updateDefinition.set("email", email));
    ofNullable(customerDataDto.phoneNumber()).ifPresent(phoneNumber -> updateDefinition.set("phoneNumber", phoneNumber));
    ofNullable(customerDataDto.street()).ifPresent(street -> updateDefinition.set("address.street", street));
    ofNullable(customerDataDto.city()).ifPresent(city -> updateDefinition.set("address.city", city));
    ofNullable(customerDataDto.zipCode()).ifPresent(zipCode -> updateDefinition.set("address.zipCode", zipCode));
    ofNullable(customerDataDto.apartmentNo()).ifPresent(apartmentNo -> updateDefinition.set("address.apartmentNo", apartmentNo));
    ofNullable(customerDataDto.buildingNo()).ifPresent(buildingNo -> updateDefinition.set("address.buildingNo", buildingNo));
    ofNullable(customerDataDto.companyName()).ifPresent(companyName -> updateDefinition.set("address.companyName", companyName));
    ofNullable(customerDataDto.nip()).ifPresent(nip -> updateDefinition.set("address.nip", nip));

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

    return reactiveMongoTemplate.findAndModify(query, updateDefinition, options, Customer.class);
  }
}
