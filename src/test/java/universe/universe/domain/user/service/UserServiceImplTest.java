package universe.universe.domain.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.domain.friend.repository.FriendRepository;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.exception.Exception400;
import universe.universe.global.exception.Exception404;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceImplTest {
    @Autowired private UserServiceImpl userService;
    @Autowired private FriendRepository friendRepository;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        friendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 회원 가입")
    void 회원_가입() throws Exception {
        // given
        UserRequestDTO.UserJoinDTO userJoinDTO = getUserJoinDTO();

        // when
        UserResponseDTO.UserJoinDTO result = userService.join(userJoinDTO);

        // then
        assertNotNull(result);
        assertEquals("test@test.com", result.getUserEmail());

        User savedUser = userRepository.findByUserEmail("test@test.com");
        assertNotNull(savedUser);
        assertEquals("test@test.com", savedUser.getUserEmail());
        assertNotEquals("1234", savedUser.getUserPassword());
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 중복 회원 가입")
    void 중복_회원_가입() throws Exception {
        // given
        UserRequestDTO.UserJoinDTO userJoinDTO = getUserJoinDTO();

        // when
        userService.join(userJoinDTO);

        // then
        assertThrows(Exception400.class, () -> userService.join(userJoinDTO));
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 회원 삭제")
    void 회원_삭제() throws Exception {
        // given
        UserRequestDTO.UserJoinDTO userJoinDTO = getUserJoinDTO();
        userService.join(userJoinDTO);

        // when
        userService.delete("test@test.com");

        // then
        assertFalse(userRepository.existsByUserEmail("test@test.com"));
    }

    @Test
    @Transactional
    @DisplayName("테스트 : 없는 회원 삭제")
    void 없는_회원_삭제() throws Exception {
        assertThrows(Exception400.class, () -> userService.delete("test@test.com"));
    }

    @Test
    @DisplayName("테스트 : 회원 찾기")
    void 회원_찾기() throws Exception {
        // given
        UserRequestDTO.UserJoinDTO userJoinDTO = getUserJoinDTO();
        userService.join(userJoinDTO);

        // when
        UserResponseDTO.UserFindDTO result = userService.findOne("test@test.com");

        // then
        assertNotNull(result);
        assertEquals("test@test.com", result.getUserEmail());
    }

    @Test
    @DisplayName("테스트 : 없는 회원 찾기")
    void 없는_회원_찾기() throws Exception {
        // when
        UserResponseDTO.UserFindDTO result = userService.findOne("test@test.com");

        // then
        assertNull(result);
    }

    private static UserRequestDTO.UserJoinDTO getUserJoinDTO() {
        UserRequestDTO.UserJoinDTO userJoinDTO = new UserRequestDTO.UserJoinDTO();
        userJoinDTO.setUserEmail("test@test.com");
        userJoinDTO.setUserPassword("1234");
        return userJoinDTO;
    }
}