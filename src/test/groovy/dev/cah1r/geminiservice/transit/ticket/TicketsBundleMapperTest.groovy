package dev.cah1r.geminiservice.transit.ticket


import dev.cah1r.geminiservice.transit.line.Line
import dev.cah1r.geminiservice.transit.route.Route
import dev.cah1r.geminiservice.transit.route.RouteMapper
import dev.cah1r.geminiservice.transit.ticket.dto.CreateTicketsBundleDto
import spock.lang.Specification

import static dev.cah1r.geminiservice.helpers.StopProvider.prepareTestStop1
import static dev.cah1r.geminiservice.helpers.StopProvider.prepareTestStop2
import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.TEN
import static java.util.UUID.randomUUID

class TicketsBundleMapperTest extends Specification {

    def 'should correctly map CreateTicketsBundleDto to TicketsBundle'() {
        given: 'test data'
        def routeId = randomUUID()
        def line = new Line(1L, 'Adventure Bay - Foggy Bottom', null)
        def origin = prepareTestStop1(line)
        def destination = prepareTestStop2(line)
        def routes = Set.of(new Route(routeId, true, ONE, origin, destination, true))

        and: 'dto to map'
        def dto = new CreateTicketsBundleDto(origin.id, destination.id, [routeId], 12, TEN, true)

        when: 'mapper method is invoked'
        def result = TicketsBundleMapper.toTicketsBundle(dto, routes)

        then: 'TicketsBundle has the same properties as dto'
        result.getId() == null
        result.getIsActive() == dto.isActive()
        result.getPrice() == dto.price()
        result.getTicketsQuantity() == dto.ticketsQuantity()
        result.getRoutes() == routes
    }

    def 'should correctly map TicketsBundle to TicketsBundleDto'() {
        given:
        def bundleId = randomUUID()
        def routeId = randomUUID()
        def line = new Line(1L, 'Adventure Bay - Foggy Bottom', null)
        def origin = prepareTestStop1(line)
        def destination = prepareTestStop2(line)
        def route = new Route(routeId, true, ONE, origin, destination, true)
        def routes = Set.of(route)

        def ticketsBundle = new TicketsBundle(bundleId, routes, 69, TEN, true)

        when:
        def result = TicketsBundleMapper.toTicketsBundleDto(ticketsBundle)

        then:
        result.id() == ticketsBundle.getId()
        result.ticketsQuantity() == ticketsBundle.getTicketsQuantity()
        result.price() == ticketsBundle.getPrice()
        result.isActive() == ticketsBundle.getIsActive()
        result.routesPreview() == [RouteMapper.toRouteViewDto(route)]
    }
}
