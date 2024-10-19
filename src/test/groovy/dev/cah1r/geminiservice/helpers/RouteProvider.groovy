package dev.cah1r.geminiservice.helpers

import dev.cah1r.geminiservice.transit.line.Line
import dev.cah1r.geminiservice.transit.route.Route
import dev.cah1r.geminiservice.transit.route.dto.CreateRouteDto

import static dev.cah1r.geminiservice.helpers.StopProvider.prepareTestStop1
import static dev.cah1r.geminiservice.helpers.StopProvider.prepareTestStop2

class RouteProvider {

    static CreateRouteDto prepareTestCreateRouteDto() {
        new CreateRouteDto(100002L, 100003L, BigDecimal.valueOf(23), true, true, 100001L)
    }

    static Route prepareTestRoute() {
        def line = new Line(id: 100001L, description: "Adventure Bay - Foggy Bottom", lineStops: null)
        def stop1 = prepareTestStop1(line)
        def stop2 = prepareTestStop2(line)

        line.setLineStops(Set.of(stop1, stop2))

        new Route(id: UUID.randomUUID(), isTicketAvailable: true, price: BigDecimal.valueOf(23), startStop: stop1, endStop: stop2, isActive: true, lineId: line.getId())
    }
}
