package dev.cah1r.geminiservice.cutomer.account.dto;

import jakarta.annotation.Nonnull;

public record EditCustomerDataDto(
    @Nonnull String firstName,
    @Nonnull String lastName,
    @Nonnull String email,
    @Nonnull Integer phoneNumber) {}
