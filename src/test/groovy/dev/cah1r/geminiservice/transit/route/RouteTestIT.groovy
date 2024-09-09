package dev.cah1r.geminiservice.transit.route

import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto
import dev.cah1r.geminiservice.transit.route.dto.RouteStatusDto
import dev.cah1r.geminiservice.transit.route.dto.TicketAvailabilityDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.UUID.randomUUID
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles('test')
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RouteTestIT extends Specification {

    static def ROUTES_URI = "/api/v1/routes"

    @Autowired WebTestClient webTestClient
    @Autowired RouteService routeService
    @Autowired RouteStatusService routeStatusService
    @Autowired RouteRepository routeRepository

    def 'should create new route'() {
        given:
        def dto = getCreateRouteDto()

        when:
        def result = webTestClient.post()
                .uri(ROUTES_URI)
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then:
        result.expectStatus().isOk()
        UUID id = result.expectBody(UUID).returnResult().responseBody

        and:
        def savedRoute = routeRepository.findById(id).get()
        savedRoute.getStartStop().getId() == dto.originStopId()
        savedRoute.getEndStop().getId() == dto.destinationStopId()
        savedRoute.getPrice() == dto.price()
        savedRoute.isTicketAvailable() == dto.isTicketAvailable()
        savedRoute.isActive() == dto.isActive()

        cleanup:
        routeRepository.deleteById(id)
    }

    @Unroll
    def 'should return NOT_FOUND status when one of the stops do not exists in db'(long originStopId, long destinationStopId) {
        given:
        def dto = new CreateRouteDto(originStopId, destinationStopId, BigDecimal.valueOf(21.37), true, true)

        when:
        def result = webTestClient.post()
                .uri(ROUTES_URI)
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then:
        result.expectStatus().isNotFound()

        where:
        originStopId | destinationStopId
        100002       | 900002
        900002       | 100002
    }

    def 'should change route status'() {
        given:
        def routeDto = getCreateRouteDto()
        def routeId = routeService.createRoute(routeDto)

        and:
        def statusDto = new RouteStatusDto(routeId, !routeDto.isActive())

        when:
        def result = webTestClient.patch().uri("${ROUTES_URI}/${routeId}/set-status")
                .contentType(APPLICATION_JSON)
                .bodyValue(statusDto)
                .exchange()

        then:
        result.expectStatus().isOk()
        !result.expectBody(Boolean).returnResult().responseBody

        and:
        routeRepository.findById(routeId).get().isActive() != routeDto.isActive()

        cleanup:
        routeRepository.deleteById(routeId)
    }

    def 'should return NOT_FOUND status when route id is invalid'() {
        given:
        def routeId = randomUUID()
        def statusDto = new RouteStatusDto(routeId, true)

        when:
        def result = webTestClient
                .patch()
                .uri("${ROUTES_URI}/${routeId}/set-status")
                .contentType(APPLICATION_JSON)
                .bodyValue(statusDto)
                .exchange()

        then:
        result.expectStatus().isNotFound()
    }

    def 'should delete route'() {
        given:
        def routeDto = getCreateRouteDto()
        def routeId = routeService.createRoute(routeDto)

        when:
        def result = webTestClient
                .delete()
                .uri("${ROUTES_URI}/${routeId}")
                .exchange()

        then:
        result.expectStatus().isNoContent()

        and:
        routeRepository.findById(routeId).isEmpty()
    }

    def 'should return NOT FOUND status when there is no route with given ID'() {
        given:
        def routeId = randomUUID()

        when:
        def result = webTestClient
                .delete()
                .uri("${ROUTES_URI}/${routeId}")
                .exchange()

        then:
        result.expectStatus().isNotFound()

        and:
        routeRepository.findById(routeId).isEmpty()
    }

    def 'should change ticket availability in route'() {
        given:
        def routeDto = getCreateRouteDto()
        def routeId = routeService.createRoute(routeDto)

        and:
        def statusDto = new TicketAvailabilityDto(false)

        when:
        def result = webTestClient.patch().uri("${ROUTES_URI}/${routeId}/set-ticket-availability")
                .contentType(APPLICATION_JSON)
                .bodyValue(statusDto)
                .exchange()

        then:
        result.expectStatus().isOk()
        !result.expectBody(Boolean).returnResult().responseBody

        and:
        routeRepository.findById(routeId).get().isTicketAvailable() != routeDto.isTicketAvailable()

        cleanup:
        routeRepository.deleteById(routeId)
    }

    def 'should return NOT FOUND when no route found for given ID'() {
        given:
        def routeId = randomUUID()

        and:
        def statusDto = new TicketAvailabilityDto(false)

        when:
        def result = webTestClient.patch().uri("${ROUTES_URI}/${routeId}/set-ticket-availability")
                .contentType(APPLICATION_JSON)
                .bodyValue(statusDto)
                .exchange()

        then:
        result.expectStatus().isNotFound()
    }

    def 'should return all saved routes'() {
        given:
        def route1 = getCreateRouteDto()
        def route2 = new CreateRouteDto(100003, 100002, BigDecimal.valueOf(21.37), true, true)

        and:
        assert routeRepository.findAll().isEmpty()

        and:
        def id1 = routeService.createRoute(route1)
        def id2 = routeService.createRoute(route2)

        when:
        def result = webTestClient.get()
                .uri { uriBuilder -> uriBuilder.path(ROUTES_URI)
                            .queryParam('keyword', null)
                            .queryParam('size', 10)
                            .queryParam('page', 0)
                            .build()
                }.exchange()

        then:
        result.expectStatus().isOk()
                .expectBody()
                .jsonPath('page.totalElements').isEqualTo(2)
                .jsonPath('page.totalPages').isEqualTo(1)
    }


    static CreateRouteDto getCreateRouteDto() {
        new CreateRouteDto(100002, 100003, BigDecimal.valueOf(21.37), true, true)
    }
}
