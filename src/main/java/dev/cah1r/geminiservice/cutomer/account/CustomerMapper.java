package dev.cah1r.geminiservice.cutomer.account;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class CustomerMapper {

  static Customer toCustomer(CustomerDataDto customerDataDto) {
    return Customer.builder()
        .firstName(customerDataDto.firstName())
        .lastName(customerDataDto.lastName())
        .email(customerDataDto.email())
        .phoneNumber(customerDataDto.phoneNumber())
        .address(createAddress(customerDataDto))
        .build();
  }

  private static Address createAddress(CustomerDataDto customerDataDto) {
    return Address.builder()
        .street(customerDataDto.street())
        .buildingNo(customerDataDto.buildingNo())
        .apartmentNo(customerDataDto.apartmentNo())
        .city(customerDataDto.city())
        .zipCode(customerDataDto.zipCode())
        .nip(customerDataDto.nip())
        .companyName(customerDataDto.companyName())
        .build();
  }
}
