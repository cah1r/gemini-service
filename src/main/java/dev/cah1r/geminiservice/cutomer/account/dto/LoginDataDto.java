package dev.cah1r.geminiservice.cutomer.account.dto;

import org.springframework.lang.NonNull;

public record LoginDataDto(@NonNull String email, @NonNull String password) {}
