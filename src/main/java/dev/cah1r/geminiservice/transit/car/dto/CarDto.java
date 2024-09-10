package dev.cah1r.geminiservice.transit.car.dto;

public record CarDto(
    Long id,
    String registration,
    Integer capacity,
    Integer idNumber,
    String name
) {}
