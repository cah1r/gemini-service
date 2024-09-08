package dev.cah1r.geminiservice.user

import dev.cah1r.geminiservice.error.exception.CustomerNotFoundException
import spock.lang.Specification

import static UserUtils.getTestUser_2
import static UserUtils.testUser_1
import static dev.cah1r.geminiservice.user.UserMapper.toUserDataDto

class UserServiceTest extends Specification {

    UserRepository userRepository = Mock()
    UserService userService = new UserService(userRepository)

    def "findUserByEmail() should return UserDataDto when user is found by email"() {
        given:
        def user = getTestUser_1()
        def userDataDto = toUserDataDto(user)
        def email = user.email

        when:
        def result = userService.findUserByEmail(email)

        then:
        result == userDataDto
        1 * userRepository.findByEmail(email) >> Optional.of(user)
        0 * _
    }

    def "findUserByEmail() should throw CustomerNotFoundException when user is not found by email"() {
        given:
        String email = 'unknown@cah1r.dev'

        when:
        userService.findUserByEmail(email)

        then:
        thrown(CustomerNotFoundException)
        1 * userRepository.findByEmail(email) >> Optional.empty()
        0 * _
    }

    def 'findAll() should return list of users'() {
        given:
        def userList = [getTestUser_1(), getTestUser_2()]
        def dtoList = userList.collect() { it -> toUserDataDto(it) }

        when:
        def result = userService.getAllUsers()

        then:
        result == dtoList
        1 * userRepository.findAll() >> userList
        0 * _
    }

}
