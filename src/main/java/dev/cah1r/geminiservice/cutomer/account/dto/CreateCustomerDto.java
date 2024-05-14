package dev.cah1r.geminiservice.cutomer.account.dto;

import jakarta.annotation.Nonnull;

public record CreateCustomerDto(
    @Nonnull String firstName,
    @Nonnull String lastName,
    @Nonnull String email,
    @Nonnull Integer phoneNumber,
    String companyName,
    String street,
    Integer buildingNo,
    Integer apartmentNo,
    String city,
    String zipCode,
    String nip) {}
