package dev.cah1r.geminiservice.user;

import dev.cah1r.geminiservice.security.CreateUserDto;
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
    return User.builder()
        .email(dto.email())
        .firstName((dto.firstName()))
        .lastName(dto.lastName())
        .phoneNumber(dto.phoneNumber())
        .password(encryptedPassword)
        .role(Role.USER)
        .build();
  }
}
