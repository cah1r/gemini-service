package dev.cah1r.geminiservice.cutomer.account;

import static java.util.Optional.ofNullable;

import dev.cah1r.geminiservice.cutomer.account.dto.CreateCustomerDto;
import dev.cah1r.geminiservice.cutomer.account.dto.CustomerDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CustomerMapper {

  Customer toCustomer(CreateCustomerDto createCustomerDto) {
    var customer =
        Customer.builder()
            .firstName(createCustomerDto.firstName())
            .lastName(createCustomerDto.lastName())
            .email(createCustomerDto.email());

    ofNullable(createCustomerDto.phoneNumber()).ifPresent(customer::phoneNumber);
    ofNullable(createAddress(createCustomerDto)).ifPresent(customer::address);

    return customer.build();
  }

  private Address createAddress(CreateCustomerDto createCustomerDto) {
    if (checkForNullInAddress(createCustomerDto)) {
      return null;
    }
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

  private boolean checkForNullInAddress(CreateCustomerDto createCustomerDto) {
    return createCustomerDto.street() == null
            || createCustomerDto.apartmentNo() == null
            || createCustomerDto.city() == null
            || createCustomerDto.buildingNo() == null
            || createCustomerDto.zipCode() == null;
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
