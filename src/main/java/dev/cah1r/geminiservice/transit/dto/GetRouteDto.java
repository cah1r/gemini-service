package dev.cah1r.geminiservice.transit.dto;

import dev.cah1r.geminiservice.transit.Line;
import dev.cah1r.geminiservice.transit.busstop.dto.GetBusStopDto;

public record GetRouteDto(
    String id,
    GetBusStopDto initialStop,
    GetBusStopDto lastStop,
    Line line,
    Integer durationInMinutes,
    Integer ticketPriceInPennies,
    boolean isActive) {}
