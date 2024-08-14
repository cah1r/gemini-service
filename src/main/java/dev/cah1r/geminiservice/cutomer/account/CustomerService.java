package dev.cah1r.geminiservice.cutomer.account;

import static java.lang.Boolean.FALSE;

import dev.cah1r.geminiservice.cutomer.account.dto.CreateCustomerDto;
import dev.cah1r.geminiservice.cutomer.account.dto.CustomerDataDto;
import dev.cah1r.geminiservice.cutomer.account.dto.EditCustomerDataDto;
import dev.cah1r.geminiservice.cutomer.account.dto.LoginDataDto;
import dev.cah1r.geminiservice.error.exception.CustomerAlreadyExistsException;
import dev.cah1r.geminiservice.error.exception.CustomerNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
class CustomerService {

  private static final String UPDATED_TSP_LABEL = "updatedTimestamp";
  private static final String EMAIL_LABEL = "email";

  private final CustomerRepository customerRepository;
  private final ReactiveMongoTemplate reactiveMongoTemplate;
  private final CustomerMapper mapper;
  private final PasswordService passwordService;

  Mono<CustomerDataDto> createCustomer(CreateCustomerDto createCustomerDto) {
    return customerRepository
        .existsByEmail(createCustomerDto.email())
        .flatMap(exists -> FALSE.equals(exists)
                    ? saveCustomerInDb(createCustomerDto)
                    : Mono.error(new CustomerAlreadyExistsException(createCustomerDto.email())));
  }

  private Mono<CustomerDataDto> saveCustomerInDb(CreateCustomerDto createCustomerDto) {
    return customerRepository
        .save(mapper.toCustomer(createCustomerDto))
        .map(CustomerMapper::toCustomerDataDto)
        .doOnNext(customer -> log.info("Saved customer in database with email: {} and id: {}",
                    customer.email(), customer.id()))
        .doOnError(err -> log.error("Could not save in database customer: {}", createCustomerDto, err));
  }

  Mono<Customer> updateCustomer(String currentEmail, EditCustomerDataDto editCustomerDataDto) {
    Query query = new Query().addCriteria(Criteria.where(EMAIL_LABEL).is(currentEmail));
    Update updateDefinition = getCustomerUpdateDefinition(editCustomerDataDto);
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);

    return reactiveMongoTemplate
        .findAndModify(query, updateDefinition, options, Customer.class)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(currentEmail)))
        .doOnNext(updated -> log.info("Updated customer with email: {} and id: {}", currentEmail, updated.getId()))
        .doOnError(err -> log.error(err.getMessage()));
  }

  private Update getCustomerUpdateDefinition(EditCustomerDataDto editCustomerDataDto) {
    return new Update()
        .set("firstName", editCustomerDataDto.firstName())
        .set("lastName", editCustomerDataDto.lastName())
        .set(EMAIL_LABEL, editCustomerDataDto.email())
        .set("phoneNumber", editCustomerDataDto.phoneNumber())
        .set(UPDATED_TSP_LABEL, LocalDateTime.now());
  }

  Mono<Customer> removeCustomerAddress(String email) {
    Query query = new Query().addCriteria(Criteria.where(EMAIL_LABEL).is(email));
    Update updateDefinition = new Update().unset("address").set(UPDATED_TSP_LABEL, LocalDateTime.now());
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);

    return reactiveMongoTemplate
        .findAndModify(query, updateDefinition, options, Customer.class)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(email)))
        .doOnNext(customer -> log.info("Successfully removed address from customer account with email: {}", email))
        .doOnError(err -> log.error("Couldn't remove address from customer account with email: {}", email, err));
  }

  Mono<Customer> addCustomerAddress(String email, Address address) {
    Query query = new Query().addCriteria(Criteria.where(EMAIL_LABEL).is(email));
    Update updateDefinition = getAddressUpdateDefinition(address);
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);

    return saveAddressInDb(email, query, updateDefinition, options);
  }

  private Mono<Customer> saveAddressInDb(String email, Query query, Update updateDefinition, FindAndModifyOptions options) {
    return reactiveMongoTemplate
        .findAndModify(query, updateDefinition, options, Customer.class)
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(email)))
        .doOnNext(customer -> log.info("Successfully added address to customer with email: {}", email))
        .doOnError(err -> log.info("Couldn't save customer with email: {} to database with added Address", email, err));
  }

  private static Update getAddressUpdateDefinition(Address address) {
    Update updateDefinition = new Update()
            .set("address.street", address.street())
            .set("address.city", address.city())
            .set("address.apartmentNo", address.apartmentNo())
            .set("address.buildingNo", address.buildingNo())
            .set("address.zipCode", address.zipCode())
            .set(UPDATED_TSP_LABEL, LocalDateTime.now());

    Optional.ofNullable(address.nip()).ifPresent(nip -> updateDefinition.set("address.nip", nip));
    Optional.ofNullable(address.companyName()).ifPresent(nip -> updateDefinition.set("address.companyName", nip));

    return updateDefinition;
  }

  Flux<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  Mono<CustomerDataDto> loginCustomer(LoginDataDto loginData) {
    return customerRepository
        .findCustomerByEmail(loginData.email())
        .switchIfEmpty(Mono.error(new CustomerNotFoundException(loginData.email())))
        .doOnNext(user -> passwordService.validatePassword(loginData, user.getPassword()))
        .map(CustomerMapper::toCustomerDataDto);
  }
}
