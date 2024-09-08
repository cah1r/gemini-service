package dev.cah1r.geminiservice.user

import dev.cah1r.geminiservice.user.dto.CreateUserDto
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import static dev.cah1r.geminiservice.user.UserHelperMethods.testUser_1
import static java.util.UUID.randomUUID

class UserMapperTest extends Specification {

    PasswordEncoder encoder = new BCryptPasswordEncoder()

    def 'should correctly map User to UserDataDto'() {
        given:
        def id = randomUUID()
        def user = testUser_1
        user.setId(id)

        when:
        def result = UserMapper.toUserDataDto(user)

        then:
        result.id() == id
        result.email() == user.getEmail()
        result.firstName() == user.getFirstName()
        result.lastName() == user.getLastName()
        result.phoneNumber() == user.getPhoneNumber()
        result.role() == user.getRole()
    }

    def 'should correctly map CreateUserDto to User'() {
        given:
        def dto = new CreateUserDto('senna@cah1r.dev', 'Ayrton', 'Senna', 124356879, 'password')
        def encryptedPassword = encoder.encode(dto.password())

        when:
        def result = UserMapper.toUser(dto, encryptedPassword)

        then:
        result.getEmail() == dto.email()
        result.getFirstName() == dto.firstName()
        result.getLastName() == dto.lastName()
        result.getPhoneNumber() == dto.phoneNumber()
        result.getRole() == Role.USER
    }
}
