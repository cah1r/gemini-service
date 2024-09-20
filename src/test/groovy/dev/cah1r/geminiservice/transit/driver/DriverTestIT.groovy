package dev.cah1r.geminiservice.transit.driver


import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto
import dev.cah1r.geminiservice.transit.driver.dto.DriverDto
import dev.cah1r.geminiservice.transit.driver.dto.DriverStatusDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static dev.cah1r.geminiservice.helpers.DriverProvider.getTestDriver2
import static dev.cah1r.geminiservice.helpers.DriverProvider.testDriver1
import static java.util.UUID.randomUUID
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class DriverTestIT extends Specification {

    def apiUrl = '/api/v1/admin/drivers'

    @Autowired WebTestClient webTestClient
    @Autowired DriverRepository driverRepository

    def 'should save new driver in db'() {
        given: 'driver dto to create'
        def dto = new CreateDriverDto('Ayrton', 'Senna', '111222333', false)

        when: 'endpoint to create driver is triggered'
        def response = webTestClient.post()
                .uri(apiUrl)
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then: 'response has 201 CREATED http status and id of created entity in body'
        def driverId = response.expectStatus().isCreated().expectBody(UUID).returnResult().responseBody

        and: 'url to get created driver is present in location header'
        response.expectHeader().value('Location', { location ->
            assert location == "${apiUrl}/${driverId}"
        })

        and: 'driver is present ind db with expected properties'
        def driver = driverRepository.findById(driverId).get()
        driver.getFirstName() == dto.firstName()
        driver.getLastName() == dto.lastName()
        driver.getPhoneNumber() == dto.phoneNumber()
        driver.getIsActive() == dto.isActive()

        cleanup:
        driverRepository.deleteById(driverId)
    }

    def 'should retrieve saved drivers from db'() {
        given:
        def driver1 = driverRepository.save(getTestDriver1())
        def driver2 = driverRepository.save(getTestDriver2())

        when:
        def response = webTestClient.get().uri(apiUrl).exchange()

        then:
        def responseBody = response
                .expectStatus().isOk()
                .expectBodyList(DriverDto).hasSize(2)
                .returnResult().responseBody

        responseBody.collect { it -> it.id() }.containsAll([driver1.getId(), driver2.getId()])

        cleanup:
        driverRepository.deleteAll([driver1, driver2])
    }

    def 'should delete existing driver'() {
        given: 'existing driver'
        def id = driverRepository.save(getTestDriver1()).getId()

        and: 'driver is present in db'
        driverRepository.findById(id).isPresent()

        when: 'endpoint to delete driver is triggered'
        def response = webTestClient.delete().uri(apiUrl + '/' + id).exchange()

        then: 'response has 204 NO CONTENT http status'
        response.expectStatus().isNoContent()

        and: 'driver is no longer present in db'
        driverRepository.findById(id).isEmpty()
    }

    def 'should change driver status'() {
        given:
        def driver = driverRepository.save(getTestDriver1())
        def dto = new DriverStatusDto(!driver.getIsActive())

        when:
        def response = webTestClient.patch()
                .uri(apiUrl + '/' + driver.getId() + '/set-active-status')
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then:
        response.expectStatus().isOk()

        and:
        driverRepository.findById(driver.getId()).get().getIsActive() == dto.isActive()

        cleanup:
        driverRepository.deleteById(driver.getId())
    }

    def 'should return 404 NOT FOUND when driver to delete is not present in db'() {
        expect:
        webTestClient.delete()
                .uri(apiUrl + '/' + randomUUID())
                .exchange()
                .expectStatus().isNotFound()
    }
}
