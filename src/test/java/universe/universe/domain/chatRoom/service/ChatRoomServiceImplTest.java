package universe.universe.domain.chatRoom.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import universe.universe.domain.friend.repository.FriendRepository;
import universe.universe.domain.friendRequest.service.FriendRequestServiceImpl;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.domain.user.service.UserServiceImpl;
import universe.universe.global.auth.jwt.JwtProperties;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ChatRoomServiceImplTest {
    @Autowired private FriendRequestServiceImpl friendRequestService;
    @Autowired private FriendRepository friendRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserServiceImpl userService;

    @Value(("${jwt.secret}"))
    private String secretKey;
    @BeforeEach
    public void beforeEach() {
        friendRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void create() {
        // given

        // when

        // then
    }

    @Test
    void delete() {
        // given

        // when

        // then
    }

    @Test
    void findAll() {
        // given

        // when

        // then
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