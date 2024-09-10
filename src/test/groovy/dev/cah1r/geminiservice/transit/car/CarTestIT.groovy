package dev.cah1r.geminiservice.transit.car

import dev.cah1r.geminiservice.transit.car.dto.CarDto
import dev.cah1r.geminiservice.transit.car.dto.CreateCarDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles('test')
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CarTestIT extends Specification {

    static CARS_URI = "/api/v1/admin/cars"

    @Autowired WebTestClient webTestClient
    @Autowired CarRepository carRepository
    @Autowired CarService carService

    def 'should create Car'() {
        given: 'car data to be created'
        def dto = getTestCreateCarDto()

        when: 'create car enpoint is called'
        def result = webTestClient
                .post()
                .uri(CARS_URI)
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then: 'status is 201 Created'
        result.expectStatus().isCreated()

        and: 'Location with URI to created car is present in the header'
        result.expectHeader().value('Location', { location ->
            assert location.contains('/api/v1/admin/cars/')
        })

        and: 'car id is in response body'
        def carId = result.expectBody(Long).returnResult().responseBody

        and: 'car is present in db'
        def car = carRepository.findById(carId).get()
        car.getRegistration() == dto.registration()
        car.getCapacity() == dto.capacity()
        car.getIdNumber() == dto.idNumber()
        car.getName() == dto.name()

        cleanup:
        carRepository.deleteById(carId)
    }

    @Unroll
    def 'should return CONFLICT status when #testCase'(String registration, Integer idNumber) {
        given: 'existing car id db'
        def existingCarId = carService.createNewCar(getTestCreateCarDto())

        and: 'car data to save in db'
        def dto = new CreateCarDto(registration, 21, idNumber, null)

        when: 'create car endpoint is called'
        def result = webTestClient
                .post()
                .uri(CARS_URI)
                .contentType(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()

        then:
        result.expectStatus().value { status -> assert status == 409 }

        cleanup:
        carRepository.deleteById(existingCarId)

        where:
        registration | idNumber | testCase
        'LU 2137Z'   | 69       | 'car with the same registration already exists'
        'XD X2137X'  | 37       | 'car with the same idNumber already exists'
    }

    def 'should delete car from db'() {
        given: 'existing car id db'
        def carId = carService.createNewCar(getTestCreateCarDto())

        when: 'delete car endpoint is called'
        def result = webTestClient.delete()
                .uri(CARS_URI + "/" + carId)
                .exchange()

        then: 'return NO CONTENT status'
        result.expectStatus().isNoContent()

        and: 'car is no longer present in db'
        carRepository.findById(carId).isEmpty()
    }

    def 'should return 404 status when cannot find object to delete'() {
        given: 'id of non existing entity'
        def id = 11

        expect: 'NOT FOUND status after called delete car endpoint'
        webTestClient.delete()
                .uri(CARS_URI + "/" + id)
                .exchange()
                .expectStatus().isNotFound()
    }

    def 'should return all entities form db'() {
        given: 'saved 2 cars'
        def carId_1 = carService.createNewCar(getTestCreateCarDto())
        def carId_2 = carService.createNewCar(new CreateCarDto('XD 2137Z', 37, 21, null))

        when: 'get all cars enpoint is called'
        def result = webTestClient.get()
                .uri(CARS_URI)
                .exchange()

        then: 'http status is 200 OK'
        result.expectStatus().isOk()

        and: 'returned list contains entities with ids of saved cars'
        result.expectBodyList(CarDto)
                .hasSize(2)
                .value { list -> list.collect() {
                    car -> car.id() } == [carId_1, carId_2]
                }

        cleanup:
        carRepository.deleteAllById([carId_1, carId_2])
    }


    static CreateCarDto getTestCreateCarDto() {
        new CreateCarDto("LU 2137Z", 21, 37, 'Żułty')
    }
}
