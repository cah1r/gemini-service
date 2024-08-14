package dev.cah1r.geminiservice.cutomer.account.dto;

import jakarta.annotation.Nonnull;

public record CreateCustomerDto(@Nonnull String email, Integer phoneNumber, String password) {}
