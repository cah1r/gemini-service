package dev.cah1r.geminiservice.transit.route;

import java.time.LocalTime;

public record ScheduleDto(LocalTime departureTime, Integer courseNo) {
}
