package dev.cah1r.geminiservice.transit.ticket


import dev.cah1r.geminiservice.transit.line.LineRepository
import dev.cah1r.geminiservice.transit.route.RouteRepository
import dev.cah1r.geminiservice.transit.route.RouteService
import dev.cah1r.geminiservice.transit.stop.StopRepository
import dev.cah1r.geminiservice.transit.ticket.dto.BundleStatusDto
import dev.cah1r.geminiservice.transit.ticket.dto.CreateTicketsBundleDto
import dev.cah1r.geminiservice.transit.ticket.dto.TicketsBundleDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static java.util.UUID.fromString
import static java.util.UUID.randomUUID
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TicketsBundleTestIT extends Specification {

    def apiUrl = "/api/v1/admin/bundles"

    @Autowired WebTestClient webTestClient
    @Autowired RouteService routeService
    @Autowired TicketsBundleRepository ticketsBundleRepository
    @Autowired RouteRepository routeRepository
    @Autowired LineRepository lineRepository
    @Autowired StopRepository stopRepository


    def 'should correctly create new ticket bundle and save it in database'() {
        given: 'existing route id in db and dto'
        def routeId = fromString('80814069-0f45-4043-958b-f064bd780656')
        def dto = new CreateTicketsBundleDto(200002, 200003, [routeId], 21, BigDecimal.valueOf(213.7), true)

        when:
        def response = webTestClient.post()
                .uri(apiUrl)
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then: 'http status in response is 201 and dto with id is present in response body'
        def id = response.expectStatus().isCreated().expectBody(TicketsBundleDto).returnResult().responseBody.id()

        and: 'tickets bundle is present in db'
        def entity = ticketsBundleRepository.findById(id).get()

        and: 'entity has the same properties as dto'
        entity.getRoutes() != null
        entity.getIsActive() == dto.isActive()
        entity.getPrice() == dto.price()
        entity.getTicketsQuantity() == dto.ticketsQuantity()

        cleanup:
        ticketsBundleRepository.deleteById(id)
    }

    def 'should get all existing ticket bundles from db'() {
        given: 'existing route in db'
        def route = routeRepository.findById(fromString('80814069-0f45-4043-958b-f064bd780656')).get()

        and: 'saved tickets bundles in db'
        def tbIds = ticketsBundleRepository.saveAll([
                new TicketsBundle(null, Set.of(route), 16, BigDecimal.valueOf(213.7), true),
                new TicketsBundle(null, Set.of(route), 32, BigDecimal.valueOf(399), true)
        ]).collect { it -> it.getId()}

        when: 'endpoint to get all tickets bundles is triggered'
        def response = webTestClient.get().uri(apiUrl).exchange()

        then: 'http status is 200 OK and response body has at least 2 TicketsBundleDtos'
        response.expectStatus().isOk()
                .expectBodyList(TicketsBundleDto)
                .returnResult().responseBody.size() >= tbIds.size()

        cleanup:
        ticketsBundleRepository.deleteAllById(tbIds)
    }

    def 'should delete existing tickets bundle'() {
        given: 'existing route in db and newly created tickets bundle'
        def route = routeRepository.findById(fromString('80814069-0f45-4043-958b-f064bd780656')).get()
        def bundleId = ticketsBundleRepository.save(new TicketsBundle(null, Set.of(route), 16, BigDecimal.valueOf(213.7), true)).getId()

        and: 'tickets bundles is present in db'
        ticketsBundleRepository.findById(bundleId).isPresent()

        when: 'delete endpoint is triggered'
        def response = webTestClient.delete().uri(apiUrl + '?id=' + bundleId).exchange()

        then: '204 NO CONTENT http status in response'
        response.expectStatus().isNoContent()

        and: 'tickets bundle is no longer present in db'
        ticketsBundleRepository.findById(bundleId).isEmpty()
    }

    def 'should send 404 NOT FOUND in response on delete tickets bundle when id is invalid'() {
        given: 'test data'
        def bundleId = randomUUID()

        when: 'delete endpoint is triggered'
        def response = webTestClient.delete().uri(apiUrl + '?id=' + bundleId).exchange()

        then: '204 NO CONTENT http status in response'
        response.expectStatus().isNotFound()
    }

    def 'should successfully set tickets bundle status'() {
        given:
        def bundleId = fromString('a3867abb-51b0-476f-ba9f-eaf87c4710d0')
        def expectedBundleStatus = !ticketsBundleRepository.findById(bundleId).get().getIsActive()
        def request = new BundleStatusDto(expectedBundleStatus)

        when:
        def response = webTestClient.patch()
                .uri("${apiUrl}/${bundleId}/set-active-status")
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()

        then:
        response.expectStatus().isOk()
        ticketsBundleRepository.findById(bundleId).get().getIsActive() == expectedBundleStatus
    }

    def 'should return 404 NO CONTENT when there is no match by given id'() {
        given:
        def bundleId = randomUUID()
        def request = new BundleStatusDto(true)

        expect:
        webTestClient.patch()
                .uri("${apiUrl}/${bundleId}/set-active-status")
                .contentType(APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isNotFound()
    }

}
