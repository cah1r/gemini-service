package dev.cah1r.geminiservice.transit.car.dto;

public record CreateCarDto(
    String registration,
    Integer capacity,
    Integer idNumber,
    String name
) {}
