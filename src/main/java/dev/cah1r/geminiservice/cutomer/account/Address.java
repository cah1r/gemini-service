package dev.cah1r.geminiservice.cutomer.account;

import com.mongodb.lang.NonNull;
import lombok.Builder;

@Builder
public record Address(
    String companyName,
    @NonNull String street,
    @NonNull Integer buildingNo,
    @NonNull Integer apartmentNo,
    @NonNull String city,
    @NonNull String zipCode,
    String nip) {}
