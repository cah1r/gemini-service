package dev.cah1r.geminiservice.cutomer.account;

public record CustomerDataDto(
    String firstName,
    String lastName,
    String email,
    Integer phoneNumber,
    String companyName,
    String street,
    Integer buildingNo,
    Integer apartmentNo,
    String city,
    String zipCode,
    String nip) {}
