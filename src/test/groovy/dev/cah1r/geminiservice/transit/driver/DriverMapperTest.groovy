package dev.cah1r.geminiservice.transit.driver

import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto
import spock.lang.Specification

class DriverMapperTest extends Specification {

    def 'should properly map CreateDriverDto to Driver'() {
        given:
        def dto = new CreateDriverDto('Ayrton', 'Senna', '123456789', false)

        when:
        def result = DriverMapper.toDriver(dto)

        then:
        result.getFirstName() == dto.firstName()
        result.getLastName() == dto.lastName()
        result.getPhoneNumber() == dto.phoneNumber()
        result.getIsActive() == dto.isActive()
    }

    def 'should properly map Driver to DriverDto'() {
        given:
        def driver = DriverUtils.getTestDriver1()

        when:
        def result = DriverMapper.toDriverDto(driver)

        then:
        result.id() == driver.getId()
        result.firstName() == driver.getFirstName()
        result.lastName() == driver.getLastName()
        result.phoneNumber() == driver.getPhoneNumber()
        result.isActive() == driver.getIsActive()
    }
}
