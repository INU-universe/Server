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
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceImplTest {
    @Autowired private UserServiceImpl userService;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    @Transactional
    @DisplayName("user join test")
    void join() throws Exception {
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
    @DisplayName("user delete test")
    void delete() throws Exception {
        // given
        UserRequestDTO.UserJoinDTO userJoinDTO = getUserJoinDTO();
        userService.join(userJoinDTO);

        // when
        userService.delete("test@test.com");

        // then
        assertFalse(userRepository.existsByUserEmail("test@test.com"));
    }

    @Test
    @DisplayName("user findOne test")
    void findOne() throws Exception {
        // given
        UserRequestDTO.UserJoinDTO userJoinDTO = getUserJoinDTO();
        userService.join(userJoinDTO);

        // when
        UserResponseDTO.UserFindDTO result = userService.findOne("test@test.com");

        // then
        assertNotNull(result);
        assertEquals("test@test.com", result.getUserEmail());
    }

    private static UserRequestDTO.UserJoinDTO getUserJoinDTO() {
        UserRequestDTO.UserJoinDTO userJoinDTO = new UserRequestDTO.UserJoinDTO();
        userJoinDTO.setUserEmail("test@test.com");
        userJoinDTO.setUserPassword("1234");
        return userJoinDTO;
    }
}