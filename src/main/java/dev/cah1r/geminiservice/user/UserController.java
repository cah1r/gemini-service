package dev.cah1r.geminiservice.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
class UserController {
    
    private final UserService userService;

    @PostMapping("/address/{userId}")
    Address addCustomerAddress(@PathVariable UUID userId, @RequestBody Address address) {
        return null;
    }

    @GetMapping("/getAll")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
