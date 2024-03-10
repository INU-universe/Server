package universe.universe.domain.friend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
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
import universe.universe.domain.friend.dto.FriendResponseDTO;
import universe.universe.domain.friend.repository.FriendRepository;
import universe.universe.domain.friendRequest.service.FriendRequestServiceImpl;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.entity.UserStatus;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.domain.user.service.UserServiceImpl;
import universe.universe.global.auth.jwt.JwtProperties;
import universe.universe.global.exception.Exception404;
import universe.universe.global.exception.Exception500;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FriendServiceImplTest {
    @Autowired private FriendRequestServiceImpl friendRequestService;
    @Autowired private FriendServiceImpl friendService;
    @Autowired private FriendRepository friendRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserServiceImpl userService;
    @Autowired private EntityManager entityManager;

    @Value(("${jwt.secret}"))
    private String secretKey;
    @BeforeEach
    public void beforeEach() {
        friendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("테스트 : 친구 목록 조회")
    void 친구_목록_조회() {
        // given
        User result1 = getUser(getUserJoinDTO("test1@test.com"));
        User result2 = getUser(getUserJoinDTO("test2@test.com"));
        User result3 = getUser(getUserJoinDTO("test3@test.com"));
        User result4 = getUser(getUserJoinDTO("test4@test.com"));

        // when
        String token = getToken(result1);

        friendRequestService.acceptURL(result2.getUserEmail(), token);
        friendRequestService.acceptURL(result3.getUserEmail(), token);
        friendRequestService.acceptURL(result4.getUserEmail(), token);

        FriendResponseDTO.FriendFindAllDTO result = friendService.findAll("test1@test.com");

        // then
        Assertions.assertThat(result.getFriendList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("테스트 : 학교에 있는 친구 목록 조회 1")
    void 학교에_있는_친구_목록_조회_1() {
        // given
        User result1 = getUser(getUserJoinDTO("test1@test.com"));
        User result2 = getUser(getUserJoinDTO("test2@test.com"));
        User result3 = getUser(getUserJoinDTO("test3@test.com"));
        User result4 = getUser(getUserJoinDTO("test4@test.com"));

        // when
        String token = getToken(result1);

        friendRequestService.acceptURL(result2.getUserEmail(), token);
        friendRequestService.acceptURL(result3.getUserEmail(), token);
        friendRequestService.acceptURL(result4.getUserEmail(), token);

        FriendResponseDTO.FriendFindInSchoolDTO result = friendService.findInSchool("test1@test.com");

        // then
        Assertions.assertThat(result.getFriendList().size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 학교에 있는 친구 목록 조회 2")
    void 학교에_있는_친구_목록_조회_2() {
        // given
        User result1 = getUser(getUserJoinDTO("test1@test.com"));
        User result2 = getUser(getUserJoinDTO("test2@test.com"));
        User result3 = getUser(getUserJoinDTO("test3@test.com"));
        User result4 = getUser(getUserJoinDTO("test4@test.com"));

        // when
        String token = getToken(result1);

        friendRequestService.acceptURL(result2.getUserEmail(), token);
        friendRequestService.acceptURL(result3.getUserEmail(), token);
        friendRequestService.acceptURL(result4.getUserEmail(), token);

        result2.updateUserStatus(UserStatus.SCHOOL);
        result3.updateUserStatus(UserStatus.SCHOOL);

        // 명시적으로 트랜잭션을 커밋하여 변경된 상태를 즉시 반영
        entityManager.flush();
        entityManager.clear();

        FriendResponseDTO.FriendFindInSchoolDTO result = friendService.findInSchool("test1@test.com");

        // then
        Assertions.assertThat(result.getFriendList().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 친구 삭제")
    void 친구_삭제() {
        // given
        User result1 = getUser(getUserJoinDTO("test1@test.com"));
        User result2 = getUser(getUserJoinDTO("test2@test.com"));
        User result3 = getUser(getUserJoinDTO("test3@test.com"));
        User result4 = getUser(getUserJoinDTO("test4@test.com"));

        // when
        String token = getToken(result1);

        friendRequestService.acceptURL(result2.getUserEmail(), token);
        friendRequestService.acceptURL(result3.getUserEmail(), token);
        friendRequestService.acceptURL(result4.getUserEmail(), token);

        friendService.delete(result1.getUserEmail(), result2.getId());
        friendService.delete(result1.getUserEmail(), result3.getId());

        FriendResponseDTO.FriendFindAllDTO result = friendService.findAll(result1.getUserEmail());

        // then
        Assertions.assertThat(result.getFriendList().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 관계 없는 친구 삭제")
    void 관계_없는_친구_삭제() {
        // given
        User result1 = getUser(getUserJoinDTO("test1@test.com"));
        User result2 = getUser(getUserJoinDTO("test2@test.com"));
        User result3 = getUser(getUserJoinDTO("test3@test.com"));
        User result4 = getUser(getUserJoinDTO("test4@test.com"));

        // when
        String token = getToken(result1);

        friendRequestService.acceptURL(result2.getUserEmail(), token);
        friendRequestService.acceptURL(result3.getUserEmail(), token);
        friendRequestService.acceptURL(result4.getUserEmail(), token);

        // then
        assertThrows(Exception500.class, () -> friendService.delete(result1.getUserEmail(), 10L));
    }

    private String getToken(User result) {
        String token = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                .withClaim("userId", result.getId())
                .sign(Algorithm.HMAC512(secretKey));
        return token;
    }

    private static UserRequestDTO.UserJoinDTO getUserJoinDTO(String userEmail) {
        UserRequestDTO.UserJoinDTO userJoinDTO = new UserRequestDTO.UserJoinDTO();
        userJoinDTO.setUserEmail(userEmail);
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