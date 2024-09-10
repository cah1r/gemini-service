package dev.cah1r.geminiservice.transit.car

import dev.cah1r.geminiservice.transit.car.dto.CreateCarDto
import spock.lang.Specification

class CarMapperTest extends Specification {

    def 'should properly map CreateCarDto to Car'() {
        given:
        def dto = new CreateCarDto("XD 2137Z", 21, 37, "Żułty")

        when:
        def result = CarMapper.toCar(dto)

        then:
        result.getRegistration() == dto.registration()
        result.getCapacity() == dto.capacity()
        result.getIdNumber() == dto.idNumber()
        result.getName() == dto.name()
    }

    def 'should properly map Car to CarDto'() {
        given:
        def car = new Car(69, 'XD 2137Z', 21, 37, 'Żułty', null)

        when:
        def result = CarMapper.toCarDto(car)

        then:
        result.id() == car.getId()
        result.registration() == car.getRegistration()
        result.capacity() == car.getCapacity()
        result.idNumber() == car.getIdNumber()
        result.name() == car.getName()
    }
}
