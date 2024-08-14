package dev.cah1r.geminiservice.cutomer.account;

import static java.util.Optional.ofNullable;

import dev.cah1r.geminiservice.cutomer.account.dto.CreateCustomerDto;
import dev.cah1r.geminiservice.cutomer.account.dto.CustomerDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CustomerMapper {

  private final PasswordService passwordService;

  Customer toCustomer(CreateCustomerDto createCustomerDto) {
    var customer =
            Customer.builder()
                .email(createCustomerDto.email())
                .password(passwordService.encryptPassword(createCustomerDto.password()));

    ofNullable(createCustomerDto.phoneNumber()).ifPresent(customer::phoneNumber);

    return customer.build();
  }

  static CustomerDataDto toCustomerDataDto(Customer customer) {
    return CustomerDataDto.builder()
        .id(customer.getId())
        .email(customer.getEmail())
        .firstName(customer.getFirstName())
        .lastName(customer.getLastName())
        .address(customer.getAddress())
        .activeTicketBundles(customer.getTicketBundles())
        .activeTickets(customer.getTickets())
        .build();
  }

  static CustomerDataDto toCustomerDataDto(GoogleUser googleUser) {
    return CustomerDataDto.builder()
            .id(googleUser.getId())
            .email(googleUser.getEmail())
            .firstName(googleUser.getFirstName())
            .lastName((googleUser.getLastName()))
            .build();
  }
}
