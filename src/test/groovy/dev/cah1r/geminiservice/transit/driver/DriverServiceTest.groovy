package dev.cah1r.geminiservice.transit.driver

import dev.cah1r.geminiservice.error.exception.DriverAlreadyExistsException
import dev.cah1r.geminiservice.error.exception.DriverNotFoundException
import dev.cah1r.geminiservice.transit.driver.dto.CreateDriverDto
import dev.cah1r.geminiservice.transit.driver.dto.DriverStatusDto
import spock.lang.Specification
import spock.lang.Unroll

import static dev.cah1r.geminiservice.helpers.DriverProvider.getTestDriver2
import static dev.cah1r.geminiservice.helpers.DriverProvider.testDriver1
import static java.util.UUID.randomUUID

class DriverServiceTest extends Specification {

    DriverRepository driverRepository = Mock(DriverRepository)
    DriverService driverService = new DriverService(driverRepository)

    def "getAllDrivers() should return list of DriverDto"() {
        given:
        def driver1 = getTestDriver1()
        def driver2 = getTestDriver2()

        def driverDto1 = DriverMapper.toDriverDto(driver1)
        def driverDto2 = DriverMapper.toDriverDto(driver2)

        when:
        def result = driverService.getAllDrivers()

        then:
        result == [driverDto1, driverDto2]
        1 * driverRepository.findAll() >> [driver1, driver2]
        0 * _
    }

    def "createDriver() should save and return new driver UUID if phone number is unique"() {
        given:
        def createDriverDto = new CreateDriverDto('Ayrton', 'Senna', '123456789', false)
        def driver = getTestDriver1()

        when:
        def result = driverService.createDriver(createDriverDto)

        then:
        result == driver.getId()
        1 * driverRepository.findDriverByPhoneNumber(createDriverDto.phoneNumber()) >> Optional.empty()
        1 * driverRepository.save(_ as Driver) >> driver
        0 * _
    }

    def "createDriver() should throw DriverAlreadyExistsException if phone number already exists"() {
        given:
        def existingDriver = getTestDriver1()
        def createDriverDto = new CreateDriverDto('Ayrton', 'Senna', '123456789', false)

        when:
        driverService.createDriver(createDriverDto)

        then:
        thrown(DriverAlreadyExistsException)
        1 * driverRepository.findDriverByPhoneNumber(createDriverDto.phoneNumber()) >> Optional.of(existingDriver)
        0 * _
    }

    def "deleteDriver() should delete the driver if found"() {
        given:
        def id = randomUUID()
        def driver = getTestDriver1()

        when:
        driverService.deleteDriver(id)

        then:
        1 * driverRepository.findById(id) >> Optional.of(driver)
        1 * driverRepository.delete(driver)
        0 * _
    }

    def "deleteDriver() should throw DriverNotFoundException if driver is not found"() {
        given:
        def id = randomUUID()

        when:
        driverService.deleteDriver(id)

        then:
        thrown(DriverNotFoundException)
        1 * driverRepository.findById(id) >> Optional.empty()
        0 * driverRepository.delete(_)
    }

    @Unroll
    def "setDriverActiveStatus() should update the driver's active status to #status if driver is found"(boolean status) {
        given:
        def id = randomUUID()
        def driver = getTestDriver1()
        def driverStatusDto = new DriverStatusDto(status)

        when:
        driverService.setDriverActiveStatus(id, driverStatusDto)

        then:
        driver.isActive == expected
        1 * driverRepository.findById(id) >> Optional.of(driver)
        1 * driverRepository.save(driver)

        where:
        status  | expected
        true    | true
        false   | false
    }

    def "setDriverActiveStatus() should throw DriverNotFoundException if driver is not found"() {
        given:
        def id = randomUUID()
        def driverStatusDto = new DriverStatusDto(true)

        when:
        driverService.setDriverActiveStatus(id, driverStatusDto)

        then:
        thrown(DriverNotFoundException)
        1 * driverRepository.findById(id) >> Optional.empty()
        0 * driverRepository.save(_)
    }
}
