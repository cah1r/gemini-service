package dev.cah1r.geminiservice.transit.driver.dto;

import java.util.UUID;

public record DriverDto(UUID id, String firstName, String lastName, String phoneNumber, Boolean isActive) {
}
