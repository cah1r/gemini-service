package dev.cah1r.geminiservice.cutomer.account;

import lombok.Builder;

@Builder
public record Address(
    String companyName,
    String street,
    int buildingNo,
    int apartmentNo,
    String city,
    String zipCode,
    String nip) {}
