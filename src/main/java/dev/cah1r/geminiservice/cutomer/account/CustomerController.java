package dev.cah1r.geminiservice.cutomer.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
class CustomerController {
    
    private final CustomerService customerService;
    
    @PostMapping("/customer/create")
    Mono<Customer> createCustomer(@RequestBody CustomerDataDto customerDataDto) {
        return customerService.createCustomer(customerDataDto);
    }

    @PutMapping("/customer/update")
    Mono<Customer> updateCustomer(@RequestParam String currentEmail, @RequestBody CustomerDataDto customerDataDto) {
        return customerService.updateCustomer(currentEmail, customerDataDto);
    }
}
