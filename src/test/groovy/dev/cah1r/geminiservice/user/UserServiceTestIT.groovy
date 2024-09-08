package dev.cah1r.geminiservice.user

import dev.cah1r.geminiservice.user.dto.UserDataDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import static UserUtils.testUser_1
import static UserUtils.testUser_2
import static dev.cah1r.geminiservice.user.UserMapper.toUserDataDto
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserServiceTestIT extends Specification {

    @Autowired UserRepository userRepository
    @Autowired UserService UserService
    @Autowired WebTestClient webTestClient

    def 'should return list of user from database'() {
        given:
        def testUser1 = userRepository.save(testUser_1)
        def testUser2 = userRepository.save(testUser_2)

        and:
        def user1dto = toUserDataDto(testUser1)
        def user2dto = toUserDataDto(testUser2)

        when:
        def response = webTestClient.get()
                .uri("/api/v1/users")
                .exchange()

        then:
        response.expectStatus().isOk()
                .expectBodyList(UserDataDto)
                .hasSize(3)
                .consumeWith { result ->
                    assert result.responseBody[1] as UserDataDto == user1dto
                    assert result.responseBody[2] as UserDataDto == user2dto
                }

        cleanup:
        userRepository.delete(testUser1)
        userRepository.delete(testUser2)
    }
}
