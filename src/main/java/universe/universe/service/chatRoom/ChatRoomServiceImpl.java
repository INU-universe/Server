package universe.universe.service.chatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception400;
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
    public ChatRoomResponseDTO.ChatRoomRelationCreateDTO create(ChatRoomRequestDTO.ChatRoomRelationCreateDTO chatRoomRelationCreateDTO) {
        try {
            List<ChatRoomRequestDTO.ChatRoomRelationUserCreateDTO> userList = chatRoomRelationCreateDTO.getUserList();
            if(userList == null) {
                throw new Exception400("userList", "null 입니다.");
            }
            ChatRoom chatRoom = new ChatRoom();
            chatRoomRepository.save(chatRoom);
            List<ChatRoomResponseDTO.ChatRoomUserListFindDTO> chatRoomUserListFindDTOList = new ArrayList<>();

            for(ChatRoomRequestDTO.ChatRoomRelationUserCreateDTO userId : userList) {
                ChatRoomRelation chatRoomRelation = chatRoomRelationRepository.save(new ChatRoomRelation(getUserId(userId.getUserId()), chatRoom));
                chatRoomUserListFindDTOList.add(new ChatRoomResponseDTO.ChatRoomUserListFindDTO(chatRoomRelation));
            }

            ChatRoomResponseDTO.ChatRoomRelationCreateDTO chatRoomRelationCreateDTOList = new ChatRoomResponseDTO.ChatRoomRelationCreateDTO(chatRoom.getId(), chatRoomUserListFindDTOList);
            return chatRoomRelationCreateDTOList;
        }
        catch (Exception e) {
            throw new Exception500("create fail : " + e.getMessage());
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
}
