package universe.universe.domain.friendRequest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.domain.friend.entity.Friend;
import universe.universe.domain.friend.repository.FriendRepository;
import universe.universe.domain.friendRequest.dto.FriendRequestResponseDTO;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.domain.user.service.UserServiceImpl;
import universe.universe.global.auth.jwt.JwtProperties;
import universe.universe.global.exception.Exception400;
import universe.universe.global.exception.Exception500;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FriendRequestServiceImplTest {
    @Autowired private FriendRequestServiceImpl friendRequestService;
    @Autowired private FriendRepository friendRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserServiceImpl userService;

    @Value(("${jwt.secret}"))
    private String secretKey;
    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 친구 요청 URL 발급")
    void 친구_요청_URL_발급() {
        // given
        User result = getUser(getUserJoinDTO1());

        // when
        FriendRequestResponseDTO.FriendRequestGetURLDTO url = friendRequestService.getURL(result.getUserEmail());

        // then
        assertNotNull(url);
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 친구 요청 URL 수락")
    void 친구_요청_URL_수락() {
        // given
        User result1 = getUser(getUserJoinDTO1());
        User result2 = getUser(getUserJoinDTO2());

        // when
        String token = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                .withClaim("userId", result1.getId())
                .sign(Algorithm.HMAC512(secretKey));

        friendRequestService.acceptURL(result2.getUserEmail(), token);
        Optional<Friend> findFromUserAndToUser = friendRepository.findByFromUserAndToUser(result1, result2);

        // then
        assertNotNull(findFromUserAndToUser);
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 중복 친구 요청 URL 수락")
    void 중복_친구_요청_URL_수락() {
        // given
        User result1 = getUser(getUserJoinDTO1());
        User result2 = getUser(getUserJoinDTO2());

        // when
        String token = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                .withClaim("userId", result1.getId())
                .sign(Algorithm.HMAC512(secretKey));

        friendRequestService.acceptURL(result2.getUserEmail(), token);

        // then
        assertThrows(Exception500.class, () -> friendRequestService.acceptURL(result2.getUserEmail(), token));
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 친구 요청 잘못된 URL 수락 1")
    void 친구_요청_잘못된_URL_수락_1() {
        // given
        User result1 = getUser(getUserJoinDTO1());
        User result2 = getUser(getUserJoinDTO2());

        // when, then
        assertThrows(Exception500.class, () -> friendRequestService.acceptURL(result2.getUserEmail(), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoVG9rZW4iLCJyb2xlIjoiUk9MRV9VU0VSIiwidXNlckVtYWlsIjoidGVzdDJAdGVzdC5jb20iLCJleHAiOjE3MTA2ODEzNDJ9.opbzs2aDn9y-zbxIoL8mxvdbGxV30Z7jGXgtfTX2hlGdDDOPi75uY_-r1XondAf0jMVP5fUmCg3cXGCeFivYQw"));
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 친구 요청 잘못된 URL 수락 2")
    void 친구_요청_잘못된_URL_수락_2() {
        // given
        User result1 = getUser(getUserJoinDTO1());
        User result2 = getUser(getUserJoinDTO2());

        // when
        String token = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                .withClaim("userId", result1.getId())
                .sign(Algorithm.HMAC512(secretKey));

        userService.delete(result1.getUserEmail());

        // then
        assertThrows(Exception500.class, () -> friendRequestService.acceptURL(result2.getUserEmail(), token));
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 친구 요청 잘못된 URL 수락 3")
    void 친구_요청_잘못된_URL_수락_3() {
        // given
        User result1 = getUser(getUserJoinDTO1());
        User result2 = getUser(getUserJoinDTO2());

        // when
        String token = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                .withClaim("userId", result1.getId())
                .sign(Algorithm.HMAC512(secretKey));

        userService.delete(result2.getUserEmail());

        // then
        assertThrows(Exception500.class, () -> friendRequestService.acceptURL(result2.getUserEmail(), token));
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 친구 요청 잘못된 URL 수락 4")
    void 친구_요청_잘못된_URL_수락_4() {
        // given
        User result1 = getUser(getUserJoinDTO1());

        // when
        String token = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                .withClaim("userId", result1.getId())
                .sign(Algorithm.HMAC512(secretKey));

        // then
        assertThrows(Exception500.class, () -> friendRequestService.acceptURL(result1.getUserEmail(), token));
    }

    private static UserRequestDTO.UserJoinDTO getUserJoinDTO1() {
        UserRequestDTO.UserJoinDTO userJoinDTO = new UserRequestDTO.UserJoinDTO();
        userJoinDTO.setUserEmail("test1@test.com");
        userJoinDTO.setUserPassword("1234");
        return userJoinDTO;
    }
    private static UserRequestDTO.UserJoinDTO getUserJoinDTO2() {
        UserRequestDTO.UserJoinDTO userJoinDTO = new UserRequestDTO.UserJoinDTO();
        userJoinDTO.setUserEmail("test2@test.com");
        userJoinDTO.setUserPassword("1234");
        return userJoinDTO;
    }
    private User getUser(UserRequestDTO.UserJoinDTO getUserJoinDTO) {
        UserRequestDTO.UserJoinDTO userJoinDTO = getUserJoinDTO;
        UserResponseDTO.UserJoinDTO result = userService.join(userJoinDTO);
        Optional<User> findUser = userRepository.findById(result.getId());
        return findUser.get();
    }
}