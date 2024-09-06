package dev.cah1r.geminiservice.transit.route


import dev.cah1r.geminiservice.transit.line.Line
import dev.cah1r.geminiservice.transit.stop.StopMapper
import spock.lang.Specification

import static dev.cah1r.geminiservice.helpers.RouteProvider.prepareTestCreateRouteDto
import static dev.cah1r.geminiservice.helpers.RouteProvider.prepareTestRoute
import static dev.cah1r.geminiservice.helpers.StopProvider.prepareTestStop1
import static dev.cah1r.geminiservice.helpers.StopProvider.prepareTestStop2

class RouteMapperTest extends Specification {

    def 'should map Route to RouteDto correctly'() {
        given:
        def route = prepareTestRoute()

        when:
        def dto = RouteMapper.toRouteDto(route)

        then:
        dto.id() == route.getId()
        dto.price() == route.getPrice()
        dto.isTicketAvailable() == route.isTicketAvailable()
        dto.originStop() == StopMapper.toStopDto(route.getStartStop())
        dto.destinationStop() == StopMapper.toStopDto(route.getEndStop())
        dto.isActive() == route.isActive()
    }

    def 'should map CreateRouteDto and stops to Route correctly'() {
        given:
        def line = new Line(id: 1L, description: "Adventure Bay - Foggy Bottom", lineStops: null)
        def stop1 = prepareTestStop1(line)
        def stop2 = prepareTestStop2(line)
        def createRouteDto = prepareTestCreateRouteDto()

        when:
        def route = RouteMapper.toRoute(createRouteDto, stop1, stop2)

        then:
        route.getPrice() == createRouteDto.price()
        route.getStartStop() == stop1
        route.getEndStop() == stop2
    }

}
