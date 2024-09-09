package dev.cah1r.geminiservice.transit.stop

import dev.cah1r.geminiservice.transit.line.Line
import dev.cah1r.geminiservice.transit.line.LineRepository
import dev.cah1r.geminiservice.transit.line.LineService
import dev.cah1r.geminiservice.transit.line.dto.CreateLineDto
import dev.cah1r.geminiservice.transit.stop.dto.CreateStopDto
import dev.cah1r.geminiservice.transit.stop.dto.StopByLineDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles('test')
@SpringBootTest(webEnvironment = RANDOM_PORT)
class StopTestIT extends Specification {

    static def STOP_URI = "/api/v1/admin/stops"

    @Autowired WebTestClient webTestClient
    @Autowired LineService lineService
    @Autowired LineRepository lineRepository
    @Autowired StopService stopService
    @Autowired StopRepository stopRepository

    def 'should create new Stop'() {
        given: 'line for new stop'
        def line = lineRepository.save(new Line(null, 'Circuit de Monaco', Set.of()))

        and: 'stop to create'
        def dto = new CreateStopDto('Monaco', 'Sainte Devote', line, 1)

        when: 'create stop endpoint is called'
        def result = webTestClient
                .post()
                .uri(STOP_URI)
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then: 'status is 201 Created'
        result.expectStatus().isCreated()

        and: 'Location header contains the URI of the newly created stop'
        def locationUri = result.expectHeader().value('Location', { location ->
            assert location.contains('/api/v1/admin/stops/')
        })

        and: 'response body contains the created stopId'
        def stopId = result.expectBody(Long).returnResult().getResponseBody()
        assert stopId != null

        and: 'Location header ends with the stopId'
        result.expectHeader().value('Location', { location ->
            assert location.endsWith("/" + stopId)
        })

        and: 'stop exists in db'
        stopRepository.findById(stopId).isPresent()

        cleanup:
        stopRepository.deleteById(stopId)
        lineRepository.delete(line)
    }

    def 'should delete existing Stop'() {
        given: 'line and stop to delete'
        def line = lineRepository.save(new Line(null, 'Circuit de Monaco', Set.of()))
        def stopId = stopService.createStop(new CreateStopDto('Monaco', 'Sainte Devote', line, 1))

        when: 'delete stop endpoint is called'
        def result = webTestClient
                .delete()
                .uri(STOP_URI + "/${stopId}")
                .exchange()

        then: 'status is 204 No Content'
        result.expectStatus().isNoContent()

        and: 'stop no longer exists in the database'
        stopRepository.findById(stopId).isEmpty()

        cleanup:
        lineRepository.delete(line)
    }

    def 'should return NOT FOUND status on delete non existing Stop'() {
        given: 'line and stop to delete'
        def stopId = 2137L

        when: 'delete stop endpoint is called'
        def result = webTestClient
                .delete()
                .uri(STOP_URI + "/${stopId}")
                .exchange()

        then: 'status is 404 Not Found'
        result.expectStatus().isNotFound()
    }

    def 'should update stop'() {
        given: 'line for stops'
        def line = lineRepository.save(new Line(null, 'Circuit de Monaco', Set.of()))

        and: 'existing stops in db'
        def stopId_1 = stopService.createStop(new CreateStopDto('Monaco', 'Sainte Devote', line, 1))
        def stopId_2 = stopService.createStop(new CreateStopDto('Monaco', 'Massenet', line, 2))
        def stopId_3 = stopService.createStop(new CreateStopDto('Monaco', 'Beau Rivage', line, 3))

        and: 'list of stops to be updated'
        def dtos = [
                new StopByLineDto(stopId_2, 'Monaco', 'Massenet', 3, line.getId()),
                new StopByLineDto(stopId_3, 'Monaco', 'Beau Rivage', 2, line.getId())
        ]

        when: 'update stops endpoint is called'
        def result = webTestClient
                .put()
                .uri(STOP_URI)
                .contentType(APPLICATION_JSON)
                .bodyValue(dtos)
                .exchange()

        then: 'status is 200 OK and body is empty'
        result.expectStatus().isOk().expectBody().isEmpty()

        and: 'line order of two stops has been updated'
        stopRepository.findById(stopId_1).get().getLineOrder() == 1
        stopRepository.findById(stopId_2).get().getLineOrder() == 3
        stopRepository.findById(stopId_3).get().getLineOrder() == 2
    }


}
