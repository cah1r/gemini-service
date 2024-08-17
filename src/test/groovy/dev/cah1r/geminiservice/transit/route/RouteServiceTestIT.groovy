package dev.cah1r.geminiservice.transit.route

import com.fasterxml.jackson.databind.ObjectMapper
import dev.cah1r.geminiservice.helpers.RouteProvider
import dev.cah1r.geminiservice.transit.route.dto.RouteStatusDto
import dev.cah1r.geminiservice.transit.stop.StopRepository
import dev.cah1r.geminiservice.transit.stop.StopService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RouteServiceTestIT extends Specification {

    @Autowired WebTestClient webTestClient
    @Autowired ObjectMapper objectMapper

    @Autowired RouteRepository routeRepository
    @Autowired StopRepository stopRepository
    @Autowired StopService stopService
    @Autowired RouteService routeService

    def path = "/api/v1/route"


    def 'should create route and save it to db'() {
        given: 'data for route to be created'
        def routeDto = RouteProvider.prepareTestCreateRouteDto()
        def routeDtoJson = objectMapper.writeValueAsString(routeDto)

        when: 'create route endpoint was called'
        def response = webTestClient.post()
                .uri(path + "/create")
                .contentType(APPLICATION_JSON)
                .bodyValue(routeDtoJson)
                .exchange()

        then: 'http status is OK'
        response.expectStatus().isOk()

        and: 'ID of created route was returned'
        def routeId = response.expectBody(UUID).returnResult().getResponseBody()

        and: 'route exists in database'
        def savedRoute = routeRepository.findById(routeId).get()
        savedRoute.getStartStop().getId() == routeDto.originStopId()
        savedRoute.getEndStop().getId() == routeDto.destinationStopId()
        savedRoute.getPrice() == routeDto.price()
        savedRoute.isTicketAvailable() == routeDto.isTicketAvailable()
        savedRoute.isActive() == routeDto.isActive()

        cleanup:
        routeRepository.deleteById(routeId)
    }

    def 'should delete route from db'() {
        given: 'route gor further removal'
        def routeDto = RouteProvider.prepareTestCreateRouteDto()
        def routeId = routeService.createRoute(routeDto)

        when: 'delete endpoint was called'
        def response = webTestClient.delete()
                .uri(path + "/delete/$routeId")
                .exchange()

        then: 'returned http status is NO CONTENT '
        response.expectStatus().isNoContent()

        and: 'route is not present in database'
        routeRepository.findById(routeId).isEmpty()
    }

    def 'should set route status'() {
        given: 'existing route and dto for change its status'
        def routeDto = RouteProvider.prepareTestCreateRouteDto()
        def routeId = routeService.createRoute(routeDto)
        def statusDto = new RouteStatusDto(routeId, false)

        when: 'set status endpoint is called'
        def response = webTestClient.patch()
                .uri(path + "/set-status")
                .bodyValue(statusDto)
                .exchange()

        then: 'http response is OK'
        response.expectStatus().isOk()

        and: 'status is in db is set accordingly to dto'
        !routeRepository.findById(routeId).get().isActive()

        cleanup:
        routeRepository.deleteById(routeId)

    }
}
