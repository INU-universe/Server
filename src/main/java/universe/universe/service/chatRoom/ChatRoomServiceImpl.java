package universe.universe.service.chatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.chatRoom.ChatRoomRequestDTO;
import universe.universe.dto.chatRoom.ChatRoomResponseDTO;
import universe.universe.entitiy.chatRoom.ChatRoom;
import universe.universe.entitiy.chatRoom.ChatRoomRelation;
import universe.universe.entitiy.user.User;
import universe.universe.repository.chatRoom.ChatRoomRelationRepository;
import universe.universe.repository.chatRoom.ChatRoomRepository;
import universe.universe.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {
    final private UserRepository userRepository;
    final private ChatRoomRepository chatRoomRepository;
    final private ChatRoomRelationRepository chatRoomRelationRepository;
    @Override
    public ChatRoomResponseDTO.ChatRoomCreateDTO create(String userEmail, ChatRoomRequestDTO.ChatRoomCreateDTO chatRoomCreateDTO) {
        try {
            List<ChatRoomRequestDTO.ChatRoomUserDTO> requestList = chatRoomCreateDTO.getUserList();
            List<ChatRoomResponseDTO.ChatRoomUserDTO> responseList = new ArrayList<>();

            ChatRoomRequestDTO.ChatRoomUserDTO chatRoomUserDTO = new ChatRoomRequestDTO.ChatRoomUserDTO();
            chatRoomUserDTO.setUserId(getUser(userEmail).getId());
            requestList.add(chatRoomUserDTO);

            ChatRoom chatRoom = new ChatRoom();
            chatRoomRepository.save(chatRoom);

            for(ChatRoomRequestDTO.ChatRoomUserDTO user : requestList) {
                User findUser = getUserId(user.getUserId());
                ChatRoomRelation chatRoomRelation = chatRoomRelationRepository.save(new ChatRoomRelation(findUser, chatRoom));
                responseList.add(new ChatRoomResponseDTO.ChatRoomUserDTO(chatRoomRelation));
            }

            ChatRoomResponseDTO.ChatRoomCreateDTO result = new ChatRoomResponseDTO.ChatRoomCreateDTO(chatRoom.getId(), responseList);
            return result;
        }
        catch (Exception e) {
            throw new Exception500("create fail : " + e.getMessage());
        }
    }

    @Override
    public void delete(String userEmail, Long chatRoomId) {
        try {
            User findUser = getUser(userEmail);
            ChatRoom findChatRoom = getChatRoom(chatRoomId);
            ChatRoomRelation findChatRoomRelation = getChatRoomRelation(findUser, findChatRoom);
            chatRoomRelationRepository.delete(findChatRoomRelation);
        } catch (Exception e) {
            throw new Exception500("delete fail : " + e.getMessage());
        }
    }


    private User getUserId(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    private User getUser(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }

    private ChatRoom getChatRoom(Long chatRoomId) {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findById(chatRoomId);
        if(!findChatRoom.isPresent()) {
            throw new Exception404("해당 채팅방을 찾을 수 없습니다.");
        }
        return findChatRoom.get();
    }
    private ChatRoomRelation getChatRoomRelation(User findUser, ChatRoom findChatRoom) {
        Optional<ChatRoomRelation> findChatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(findUser, findChatRoom);
        if(!findChatRoomRelation.isPresent()) {
            throw new Exception404("해당 관계를 찾을 수 없습니다.");
        }
        return findChatRoomRelation.get();
    }
}
