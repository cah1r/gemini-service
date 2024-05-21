package dev.cah1r.geminiservice.cutomer.account;


import dev.cah1r.geminiservice.cutomer.account.dto.CreateCustomerDto;
import dev.cah1r.geminiservice.cutomer.account.dto.CustomerDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CustomerMapper {

  private final PasswordEncoder passwordEncoder;

  Customer toCustomer(CreateCustomerDto createCustomerDto) {
    return Customer.builder()
        .firstName(createCustomerDto.firstName())
        .lastName(createCustomerDto.lastName())
        .email(createCustomerDto.email())
//        .password(passwordEncoder.encode(createCustomerDto.password()))
        .phoneNumber(createCustomerDto.phoneNumber())
        .address(createAddress(createCustomerDto))
        .build();
  }

  private Address createAddress(CreateCustomerDto createCustomerDto) {
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
