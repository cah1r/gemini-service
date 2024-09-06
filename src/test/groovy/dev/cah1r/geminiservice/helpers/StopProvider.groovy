package dev.cah1r.geminiservice.helpers

import dev.cah1r.geminiservice.transit.line.Line
import dev.cah1r.geminiservice.transit.stop.Stop

class StopProvider {

    static Stop prepareTestStop1(Line line) {
        new Stop(id: 100002L, town: 'Adventure Bay', details: 'Paw Patrol Quarters', line: line, lineOrder: 1, schedules: null)
    }

    static Stop prepareTestStop2(Line line) {
        new Stop(id: 100003L, town: 'Foggy Bottom', details: 'Humdingers Cave' , line: line, lineOrder: 2, schedules: null)
    }
}
