package dev.cah1r.geminiservice.transit.stop;

import dev.cah1r.geminiservice.transit.route.Schedule;
import dev.cah1r.geminiservice.transit.stop.dto.StopByLineDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopDto;
import dev.cah1r.geminiservice.transit.stop.dto.StopWithSchedulesDto;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StopMapper {

  public static StopWithSchedulesDto toStopWithSchedulesDto(Stop stop) {
    return new StopWithSchedulesDto(
        stop.getId(),
        stop.getTown(),
        stop.getDetails(),
        stop.getLineOrder(),
        getDepartures(stop)
    );
  }

  private static List<LocalTime> getDepartures(Stop stop) {
    return ofNullable(stop.getSchedules())
        .map(schedules -> schedules.stream()
            .map(Schedule::getDepartureTime)
            .toList())
        .orElse(List.of());
  }

  public static StopDto toStopDto(Stop stop) {
    return StopDto.builder()
        .id(stop.getId())
        .town(stop.getTown())
        .details(stop.getDetails())
        .lineOrder(stop.getLineOrder())
        .build();
  }

  public static StopByLineDto toStopByLineDto(Stop stop, Long lineId) {
    return new StopByLineDto(
        stop.getId(),
        stop.getTown(),
        stop.getDetails(),
        stop.getLineOrder(),
        lineId
    );
  }
}
