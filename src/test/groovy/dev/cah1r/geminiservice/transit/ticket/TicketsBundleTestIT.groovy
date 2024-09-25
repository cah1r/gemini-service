package dev.cah1r.geminiservice.transit.ticket


import dev.cah1r.geminiservice.transit.line.Line
import dev.cah1r.geminiservice.transit.line.LineRepository
import dev.cah1r.geminiservice.transit.route.Route
import dev.cah1r.geminiservice.transit.route.RouteRepository
import dev.cah1r.geminiservice.transit.route.RouteService
import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto
import dev.cah1r.geminiservice.transit.stop.Stop
import dev.cah1r.geminiservice.transit.stop.StopRepository
import dev.cah1r.geminiservice.transit.ticket.dto.BundleStatusDto
import dev.cah1r.geminiservice.transit.ticket.dto.CreateTicketsBundleDto
import dev.cah1r.geminiservice.transit.ticket.dto.TicketsBundleDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

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
        given:
        def routeId = routeService.createRoute(new CreateRouteDto(100002, 100003, BigDecimal.valueOf(16), true, true))
        def dto = new CreateTicketsBundleDto(100002, 100003, [routeId], 21, BigDecimal.valueOf(213.7), true)

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
        routeRepository.deleteById(routeId)
    }

    def 'should get all existing ticket bundles from db'() {
        given: 'all needed test data'
        def line = lineRepository.save(new Line(null, 'Circuit de Monaco', null))
        def origin = stopRepository.save(new Stop(123, 'Monaco', 'Sainte Devote', null, null, line, 1, null))
        def destination = stopRepository.save(new Stop(129, 'Monaco', 'Grand Hotel Hairpin', null, null, line, 6, null))
        def route = routeRepository.save(new Route(null, true, BigDecimal.valueOf(16), origin, destination, true))

        and: 'saved tickets bundles in db'
        def tbIds = ticketsBundleRepository.saveAll([
                new TicketsBundle(null, Set.of(route), 16, BigDecimal.valueOf(213.7), true),
                new TicketsBundle(null, Set.of(route), 32, BigDecimal.valueOf(399), true)
        ]).collect { it -> it.getId()}


        when: 'endpoint to get all tickets bundles is triggered'
        def response = webTestClient.get().uri(apiUrl).exchange()

        then: 'http status is 200 OK and response body has 2 TicketsBundleDto'
        response.expectStatus()
                .isOk()
                .expectBodyList(TicketsBundleDto).hasSize(2)
                .returnResult().responseBody

        cleanup:
        ticketsBundleRepository.deleteAllById(tbIds)
        routeRepository.delete(route)
        stopRepository.deleteAll([origin, destination])
        lineRepository.delete(line)
    }

    def 'should delete existing tickets bundle'() {
        given: 'test data'
        def line = lineRepository.save(new Line(null, 'Circuit de Monaco', null))
        def origin = stopRepository.save(new Stop(123, 'Monaco', 'Sainte Devote', null, null, line, 1, null))
        def destination = stopRepository.save(new Stop(129, 'Monaco', 'Grand Hotel Hairpin', null, null, line, 6, null))
        def route = routeRepository.save(new Route(null, true, BigDecimal.valueOf(16), origin, destination, true))
        def bundleId = ticketsBundleRepository.save(new TicketsBundle(null, Set.of(route), 16, BigDecimal.valueOf(213.7), true)).getId()

        and: 'tickets bundles is present in db'
        ticketsBundleRepository.findById(bundleId).isPresent()

        when: 'delete endpoint is triggered'
        def response = webTestClient.delete().uri(apiUrl + '?id=' + bundleId).exchange()

        then: '204 NO CONTENT http status in response'
        response.expectStatus().isNoContent()

        and: 'tickets bundle is no longer present in db'
        ticketsBundleRepository.findById(bundleId).isEmpty()

        cleanup:
        routeRepository.delete(route)
        stopRepository.deleteAll([origin, destination])
        lineRepository.delete(line)
    }

    def 'should send 404 NOT FOUND in response on delete tickets bundle when id is invalid'() {
        given: 'test data'
        def bundleId = randomUUID()

        when: 'delete endpoint is triggered'
        def response = webTestClient.delete().uri(apiUrl + '?id=' + bundleId).exchange()

        then: '204 NO CONTENT http status in response'
        response.expectStatus().isNotFound()
    }

    def 'should succesfully change tickets bundle status'() {
        given: 'test data'
        def line = lineRepository.save(new Line(null, 'Circuit de Monaco', null))
        def origin = stopRepository.save(new Stop(123, 'Monaco', 'Sainte Devote', null, null, line, 1, null))
        def destination = stopRepository.save(new Stop(129, 'Monaco', 'Grand Hotel Hairpin', null, null, line, 6, null))
        def route = routeRepository.save(new Route(null, true, BigDecimal.valueOf(16), origin, destination, true))
        def bundleId = ticketsBundleRepository.save(new TicketsBundle(null, Set.of(route), 16, BigDecimal.valueOf(213.7), true)).getId()

        when:
        webTestClient.patch()
                .uri("${apiUrl}/${bundleId}/set-active-status")
                .contentType(APPLICATION_JSON)
                .bodyValue(new BundleStatusDto(false))
                .exchange()

        then:
        ticketsBundleRepository.findById(bundleId).get().getIsActive() == false

        cleanup:
        ticketsBundleRepository.deleteById(bundleId)
        routeRepository.delete(route)
        stopRepository.deleteAll([origin, destination])
        lineRepository.delete(line)
    }

}
