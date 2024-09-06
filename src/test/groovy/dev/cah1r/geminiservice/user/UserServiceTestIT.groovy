package dev.cah1r.geminiservice.user

import com.fasterxml.jackson.databind.ObjectMapper
import dev.cah1r.geminiservice.config.JwtTokenUtil
import dev.cah1r.geminiservice.user.dto.CreateUserDto
import dev.cah1r.geminiservice.user.dto.UserCredentialsDto
import dev.cah1r.geminiservice.user.dto.UserDataDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserServiceTestIT extends Specification {

    @LocalServerPort int port

    @Autowired WebTestClient webTestClient
    @Autowired ObjectMapper objectMapper

    @Autowired UserRepository userRepository
    @Autowired PasswordEncoder passwordEncoder
    @Autowired UserService userService
    @Autowired JwtTokenUtil jwtTokenUtil

    def signupURL = "/api/v1/auth/signup"
    def loginURL = "/api/v1/auth/login"

    def 'should create new user'() {

        given: 'user data to create a new user'
        def createUserDto = new CreateUserDto(
                'test@example.com',
                'john',
                'wick',
                123456789,
                'password123')

        def createUserDtoJson = objectMapper.writeValueAsString(createUserDto)

        when: 'the endpoint is called'
        def response = webTestClient.post()
                .uri(signupURL)
                .contentType(APPLICATION_JSON)
                .bodyValue(createUserDtoJson)
                .exchange()

        then: 'the response status is OK and the user is created'
        response.expectStatus().isOk()
        response.expectBody().jsonPath('$.email').isEqualTo(createUserDto.email())

        and: 'jwt token is present in header'
        response.expectHeader().exists(AUTHORIZATION)
        response.returnResult(UserDataDto).getResponseHeaders().getFirst(AUTHORIZATION).startsWith("Bearer ")

        and: 'the user is saved in the database'
        def savedUser = userRepository.findByEmail(createUserDto.email()).get()
        savedUser != null
        savedUser.email == createUserDto.email()
        savedUser.phoneNumber == createUserDto.phoneNumber()
        savedUser.firstName == createUserDto.firstName()
        savedUser.lastName == createUserDto.lastName()
        passwordEncoder.matches(createUserDto.password(), savedUser.password)

        cleanup:
        userRepository.delete(savedUser)
    }

    def 'should successfully login user'() {

        given: 'user credentials for existing user'
        def credentials = new UserCredentialsDto("s.murdoch@example.com", "test")
        def credentialsJson = objectMapper.writeValueAsString(credentials)

        when: 'the endpoint is called'
        def response = webTestClient.post()
                .uri(loginURL)
                .contentType(APPLICATION_JSON)
                .bodyValue(credentialsJson)
                .exchange()

        then: 'the response status is OK'
        response.expectStatus().isOk()
        response.expectBody().jsonPath('$.email').isEqualTo(credentials.email())

        and: 'jwt token is present in header'
        response.expectHeader().exists(AUTHORIZATION)
        response.returnResult(UserDataDto).getResponseHeaders().getFirst(AUTHORIZATION).startsWith("Bearer ")
    }

    @Unroll
    def 'should return #status status when #testCase'(int status, String testCase) {

        given: 'credentials of user to be created'
        def credentials = new UserCredentialsDto(email, "my_password")
        def credentialsJson = objectMapper.writeValueAsString(credentials)

        when: 'the login endpoint is called'
        def response = webTestClient.post()
                .uri(loginURL)
                .contentType(APPLICATION_JSON)
                .bodyValue(credentialsJson)
                .exchange()

        then: 'the response status is NOT FOUND'
        response.expectStatus().isEqualTo(status)

        where:
        email                   | status  | testCase
        'invalid@example.com'   | 404     | 'there is no user with given email'
        's.murdoch@example.com' | 401     | 'password is not matching'
    }

    def 'should return CONFLICT status on sign up when user with the same email already exists in db'() {

        given: 'user data to create'
        def createUserDto = new CreateUserDto("s.murdoch@example.com", "S", "Murdoch", 987654321, "password")
        def createUserDtoJson = objectMapper.writeValueAsString(createUserDto)

        when: 'sign up endpoint is called with given user data'
        def response = webTestClient.post()
                .uri(signupURL)
                .contentType(APPLICATION_JSON)
                .bodyValue(createUserDtoJson)
                .exchange()

        then: 'status CONFLICT was returned'
        response.expectStatus().isEqualTo(409)
    }

}
