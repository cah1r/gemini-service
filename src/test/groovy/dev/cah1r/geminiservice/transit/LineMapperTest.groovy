package dev.cah1r.geminiservice.transit


import spock.lang.Specification

class LineMapperTest extends Specification {

    def "should map Line to LineDto correctly with null line stops"() {
        given:
        def line = new Line(69L, "Adventure Bay - Foggy Bottom", null)

        when:
        def dto = LineMapper.toLineDto(line)

        then:
        dto.id() == line.getId()
        dto.description() == line.getDescription()
        dto.stops() == []
    }
}
