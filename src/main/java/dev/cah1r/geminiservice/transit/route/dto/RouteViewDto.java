package dev.cah1r.geminiservice.transit.route.dto;

import java.util.UUID;

public record RouteViewDto(
    UUID id,
    String stopATown,
    String stopADetails,
    String stopBTown,
    String stopBDetails
) { }