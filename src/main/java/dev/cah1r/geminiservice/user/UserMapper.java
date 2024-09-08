package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.user.dto.CreateUserDto;
import dev.cah1r.geminiservice.user.dto.UserDataDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

  static UserDataDto toUserDataDto(User user) {
    return UserDataDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .phoneNumber(user.getPhoneNumber())
        .role(user.getRole())
        .build();
  }

  public static User toUser(CreateUserDto dto, String encryptedPassword) {
    User user = new User();
    user.setEmail(dto.email());
    user.setFirstName((dto.firstName()));
    user.setLastName(dto.lastName());
    user.setPhoneNumber(dto.phoneNumber());
    user.setPassword(encryptedPassword);
    user.setRole(Role.USER);
    return user;
  }
}
