package dev.cah1r.geminiservice.transit

import dev.cah1r.geminiservice.transit.line.LineRepository
import dev.cah1r.geminiservice.transit.line.LineService
import dev.cah1r.geminiservice.transit.line.dto.CreateLineDto
import dev.cah1r.geminiservice.transit.line.dto.LineDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification
import spock.lang.Stepwise

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class LineTestIT extends Specification {

    @Autowired LineService lineService
    @Autowired LineRepository lineRepository
    @Autowired WebTestClient webTestClient

    def apiPath = '/api/v1/admin/lines'


    def 'should create line'() {
        given: 'dto for line to be created'
        def lineDto = new CreateLineDto("Foggy Bottom - Adventure Bay")

        when: 'create line endpoint is called'
        def response = webTestClient.post()
                .uri(apiPath)
                .contentType(APPLICATION_JSON)
                .bodyValue(lineDto)
                .exchange()

        then: 'response is OK and id of created line is in response body'
        response.expectStatus().isOk()
        def lineId = response.returnResult(LineDto).getResponseBody().blockFirst().id()

        and: 'created line is present in database'
        def savedLine = lineRepository.findById(lineId).get()
        savedLine.description == lineDto.description()

        cleanup:
        lineRepository.deleteById(lineId)
    }

    def 'should return CONFLICT where line with the same description already exists'() {

        given: 'line to be created with same description as existing one'
        def lineDto = new CreateLineDto('Adventure Bay - Foggy Bottom')

        when: 'create line endpoint is called'
        def response = webTestClient.post()
                .uri(apiPath)
                .contentType(APPLICATION_JSON)
                .bodyValue(lineDto)
                .exchange()

        then: 'CONFLICT http status is returned'
        response.expectStatus().isEqualTo(409)
    }

    def 'should retrieve all lines from db'() {
        given: 'check how many items are in db table'
        def count = lineRepository.findAll().size()

        when: 'get all endpoint is called'
        def response = webTestClient.get().uri(apiPath).exchange()

        then: 'http status is OK'
        response.expectStatus().isOk()

        and: 'response contains all items from db table'
        count > 0
        response.expectBodyList(LineDto).hasSize(count)
    }
}
