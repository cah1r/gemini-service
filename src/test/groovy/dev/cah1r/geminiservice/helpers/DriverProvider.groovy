package dev.cah1r.geminiservice.helpers

import dev.cah1r.geminiservice.transit.driver.Driver

import static java.util.UUID.randomUUID

class DriverProvider {

    static Driver getTestDriver1() {
        new Driver(
                id: randomUUID(),
                firstName: 'Ayrton',
                lastName: 'Senna',
                phoneNumber: '123456789',
                isActive: false
        )
    }

    static Driver getTestDriver2() {
        new Driver(
                id: randomUUID(),
                firstName: 'Charles',
                lastName: 'Leclerc',
                phoneNumber: '987654321',
                isActive: true
        )
    }
}
