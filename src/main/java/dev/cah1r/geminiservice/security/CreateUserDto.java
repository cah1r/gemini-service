package dev.cah1r.geminiservice.security;

import jakarta.annotation.Nonnull;

public record CreateUserDto(
        @Nonnull String email,
        @Nonnull String firstName,
        @Nonnull String lastName,
        @Nonnull Integer phoneNumber,
        @Nonnull String password
) {
}