package dev.cah1r.geminiservice.cutomer.account

import dev.cah1r.geminiservice.cutomer.account.dto.CreateCustomerDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import spock.lang.Specification
import spock.lang.Stepwise

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CustomerServiceTestIT extends Specification {

    @LocalServerPort int port

    @Autowired CustomerRepository customerRepository
    @Autowired CustomerMapper customerMapper
    @Autowired ReactiveMongoTemplate reactiveMongoTemplate
    @Autowired TestRestTemplate restTemplate

    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.13-rc0")

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl())
        registry.add("spring.data.mongodb.database", () -> "testdb")
    }

    def setupSpec() {
        mongoDBContainer.start()
    }

    def setup() {
        customerRepository.deleteAll().block()
    }

    def 'should create new customer using Google with only email present'() {

        given: 'mocked endpoint and customer data to create a new customer'
        def endpoint = "http://localhost:$port/api/v1/customer/createGoogleUser"
        def createCustomerDto = new CreateCustomerDto('test@example.com', null)

        when: 'the endpoint is called'
        def response = restTemplate.postForEntity(endpoint, createCustomerDto, CreateCustomerDto)

        then: 'the response status is OK and the customer is created'
        response.getStatusCode() == HttpStatus.OK
        response.getBody() != null
        response.getBody().email() == createCustomerDto.email()

        and:'the customer is saved in database'
        def savedCustomer = customerRepository.findCustomerByEmail(createCustomerDto.email()).block()
        savedCustomer != null
        savedCustomer.email == createCustomerDto.email()
    }

    def cleanup() {
        customerRepository.deleteAll().block()
    }

}
