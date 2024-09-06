package dev.cah1r.geminiservice.transit.stop

import dev.cah1r.geminiservice.transit.line.Line
import dev.cah1r.geminiservice.transit.route.Schedule
import spock.lang.Specification

import java.time.LocalTime

class StopMapperTest extends Specification {

    def "should map Stop to StopWithSchedulesDto correctly"() {
        given:
        def stop = prepareTestStop()

        when:
        def dto = StopMapper.toStopWithSchedulesDto(stop)

        then:
        dto.id() == stop.getId()
        dto.town() == stop.getTown()
        dto.details() == stop.getDetails()
        dto.lineOrder() == stop.getLineOrder()
        dto.departures() == stop.schedules.collect { it.departureTime }
    }

    def "should map Stop to StopByLineDto correctly"() {
        given:
        def stop = prepareTestStop()

        when:
        def dto = StopMapper.toStopByLineDto(stop, stop.line.id)

        then:
        dto.id() == stop.getId()
        dto.town() == stop.getTown()
        dto.details() == stop.getDetails()
        dto.lineOrder() == stop.getLineOrder()
        dto.lineId() == stop.getLine().getId()
    }

    def "should map Stop to StopDto correctly"() {
        given:
        def stop = prepareTestStop()

        when:
        def dto = StopMapper.toStopDto(stop)

        then:
        dto.id() == stop.getId()
        dto.town() == stop.getTown()
        dto.details() == stop.getDetails()
        dto.lineOrder() == stop.getLineOrder()
    }


    private static Stop prepareTestStop() {
        def line = new Line(1L, 'Adventure Bay - Foggy Bottom', null)
        def stop = new Stop(id: 69L, town: "Adventure Bay", details: "Central Station", lineOrder: 1, line: line)
        stop.setSchedules(Set.of(
                Schedule.builder().id(4).courseNo(1).departureTime(LocalTime.of(8, 30)).stop(stop).build(),
                Schedule.builder().id(5).courseNo(2).departureTime(LocalTime.of(10, 30)).stop(stop).build()
        ))
        line.setLineStops(Set.of(stop))
        stop
    }

}
