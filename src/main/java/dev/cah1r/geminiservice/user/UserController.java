package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.user.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
class UserController {
    
    private final UserService userService;

    @GetMapping
    List<UserDataDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
