package dev.cah1r.geminiservice.cutomer.account;

import dev.cah1r.geminiservice.cutomer.account.dto.CreateCustomerDto;
import dev.cah1r.geminiservice.cutomer.account.dto.CustomerDataDto;
import dev.cah1r.geminiservice.cutomer.account.dto.EditCustomerDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
class CustomerController {
    
    private final CustomerService customerService;
    
    @PostMapping
    Mono<CustomerDataDto> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        return customerService.createCustomer(createCustomerDto);
    }

    @PutMapping
    Mono<Customer> updateCustomer(@RequestParam String currentEmail, @RequestBody EditCustomerDataDto editCustomerDataDto) {
        return customerService.updateCustomer(currentEmail, editCustomerDataDto);
    }

    @DeleteMapping("/address")
    Mono<Customer> removeCustomerAddress(@RequestParam String email) {
        return customerService.removeCustomerAddress(email);
    }

    @PutMapping("/address")
    Mono<Customer> addCustomerAddress(@RequestParam String email, @RequestBody Address address) {
        return customerService.addCustomerAddress(email, address);
    }

    @GetMapping("/getAll")
    Flux<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
