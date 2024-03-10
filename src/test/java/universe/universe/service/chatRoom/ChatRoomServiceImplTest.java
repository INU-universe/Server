package universe.universe.service.chatRoom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.domain.chatRoom.service.ChatRoomServiceImpl;
import universe.universe.domain.user.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ChatRoomServiceImplTest {
    @Autowired private ChatRoomServiceImpl chatRoomService;
    @Autowired private UserRepository userRepository;

    @Test
    @Transactional
    @DisplayName("채팅방 개설 테스트")
    void create() {
//        // 사용자 생성
//        User user1 = userRepository.save(new User("test1@naver.com", "1234", "test1"));
//        User user2 = userRepository.save(new User("test2@naver.com", "1234", "test2"));
//        User user3 = userRepository.save(new User("test3@naver.com", "1234", "test3"));
//
//        // DTO 생성 및 사용자 추가
//        ChatRoomRequestDTO.ChatRoomCreateDTO chatRoomCreateDTO = new ChatRoomRequestDTO.ChatRoomCreateDTO();
//        List<ChatRoomRequestDTO.ChatRoomUserDTO> userList = new ArrayList<>();
//
//        ChatRoomRequestDTO.ChatRoomUserDTO chatRoomUserDTO1 = new ChatRoomRequestDTO.ChatRoomUserDTO();
//        chatRoomUserDTO1.setUserId(user1.getId());
//
//        ChatRoomRequestDTO.ChatRoomUserDTO chatRoomUserDTO2 = new ChatRoomRequestDTO.ChatRoomUserDTO();
//        chatRoomUserDTO2.setUserId(user2.getId());
//
//        ChatRoomRequestDTO.ChatRoomUserDTO chatRoomUserDTO3 = new ChatRoomRequestDTO.ChatRoomUserDTO();
//        chatRoomUserDTO3.setUserId(user3.getId());
//
//        userList.add(chatRoomUserDTO1);
//        userList.add(chatRoomUserDTO2);
//        userList.add(chatRoomUserDTO3);
//
//        chatRoomCreateDTO.setUserList(userList);
//
//        // 채팅방 생성
//        ChatRoomResponseDTO.ChatRoomCreateDTO createChatRoomRelation = chatRoomService.create(chatRoomCreateDTO);
//
//        // 결과 확인
//        System.out.println("[Start2]");
//        System.out.println("createChatRoomRelation.getChatRoomId() = " + createChatRoomRelation.getChatRoomId());
//        System.out.println("createChatRoomRelation.getUserList().size() = " + createChatRoomRelation.getUserList().size());
//        for(int i=0; i<createChatRoomRelation.getUserList().size(); i++) {
//            System.out.println("createChatRoomRelation = " + createChatRoomRelation.getUserList().get(i).getUserEmail());
//        }
//        System.out.println("[End2]");
    }
}