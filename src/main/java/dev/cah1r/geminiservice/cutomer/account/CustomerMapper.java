package dev.cah1r.geminiservice.cutomer.account;

import static lombok.AccessLevel.PRIVATE;

import dev.cah1r.geminiservice.cutomer.account.dto.CreateCustomerDto;
import dev.cah1r.geminiservice.cutomer.account.dto.CustomerDataDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class CustomerMapper {

  static Customer toCustomer(CreateCustomerDto createCustomerDto) {
    return Customer.builder()
        .firstName(createCustomerDto.firstName())
        .lastName(createCustomerDto.lastName())
        .email(createCustomerDto.email())
        .phoneNumber(createCustomerDto.phoneNumber())
        .address(createAddress(createCustomerDto))
        .build();
  }

  private static Address createAddress(CreateCustomerDto createCustomerDto) {
    return Address.builder()
        .street(createCustomerDto.street())
        .buildingNo(createCustomerDto.buildingNo())
        .apartmentNo(createCustomerDto.apartmentNo())
        .city(createCustomerDto.city())
        .zipCode(createCustomerDto.zipCode())
        .nip(createCustomerDto.nip())
        .companyName(createCustomerDto.companyName())
        .build();
  }

  static CustomerDataDto toCustomerDataDto(Customer customer) {
    return CustomerDataDto.builder()
        .id(customer.getId())
        .email(customer.getEmail())
        .firstName(customer.getFirstName())
        .lastName(customer.getLastName())
        .address(customer.getAddress())
        .activeTicketBundles(customer.getActiveTicketBundles())
        .activeTickets(customer.getActiveTickets())
        .build();
  }
}
