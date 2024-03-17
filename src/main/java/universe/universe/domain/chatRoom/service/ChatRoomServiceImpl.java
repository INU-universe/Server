package universe.universe.domain.chatRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.exception.Exception500;
import universe.universe.domain.chatRoom.dto.ChatRoomRequestDTO;
import universe.universe.domain.chatRoom.dto.ChatRoomResponseDTO;
import universe.universe.domain.chatRoom.entity.ChatRoom;
import universe.universe.domain.chatRoomRelation.entity.ChatRoomRelation;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.chatRoomRelation.repository.ChatRoomRelationRepository;
import universe.universe.domain.chatRoom.repository.ChatRoomRepository;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.reponse.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {
    final private UserRepository userRepository;
    final private ChatRoomRepository chatRoomRepository;
    final private ChatRoomRelationRepository chatRoomRelationRepository;
    @Override
    @Transactional
    public ChatRoomResponseDTO.ChatRoomCreateDTO create(String userEmail, ChatRoomRequestDTO.ChatRoomCreateDTO chatRoomCreateDTO) {
        try {
            log.info("[ChatRoomServiceImpl] create");
            List<ChatRoomRequestDTO.ChatRoomUserDTO> requestList = chatRoomCreateDTO.getUserList();
            List<ChatRoomResponseDTO.ChatRoomUserDTO> responseList = new ArrayList<>();
            Long findUserId = getUser("email", userEmail).getId();

            checkRequestList(requestList, findUserId);

            ChatRoomRequestDTO.ChatRoomUserDTO chatRoomUserDTO = new ChatRoomRequestDTO.ChatRoomUserDTO();
            chatRoomUserDTO.setUserId(findUserId);
            requestList.add(chatRoomUserDTO);

            ChatRoom chatRoom = new ChatRoom();

            chatRoomRepository.save(chatRoom);

            addResponseList(requestList, responseList, chatRoom);
            ChatRoomResponseDTO.ChatRoomCreateDTO result = new ChatRoomResponseDTO.ChatRoomCreateDTO(chatRoom.getId(), responseList);
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] ChatRoomServiceImpl create");
            throw ce;
        }
        catch (Exception e) {
            throw new Exception500("ChatRoomServiceImpl create fail : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(String userEmail, Long chatRoomId) {
        try {
            log.info("[ChatRoomServiceImpl] delete");
            User findUser = getUser("email", userEmail);
            ChatRoom findChatRoom = getChatRoom_Id(chatRoomId);
            ChatRoomRelation findChatRoomRelation = getChatRoomRelation(findUser, findChatRoom);
            chatRoomRelationRepository.delete(findChatRoomRelation);
        } catch (CustomException ce){
            log.info("[CustomException] ChatRoomServiceImpl delete");
            throw ce;
        } catch (Exception e) {
            throw new Exception500("ChatRoomServiceImpl delete fail : " + e.getMessage());
        }
    }

    @Override
    public ChatRoomResponseDTO.ChatRoomFindAllDTO findAll(String userEmail) {
        try {
            log.info("[ChatRoomServiceImpl] findAll");
            User findUser = getUser("email", userEmail);
            ChatRoomResponseDTO.ChatRoomFindAllDTO result = chatRoomRelationRepository.ChatRoomRelationFindAll(findUser.getId());
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] ChatRoomServiceImpl findAll");
            throw ce;
        } catch (Exception e) {
            throw new Exception500("ChatRoomServiceImpl findAll fail : " + e.getMessage());
        }
    }


    private User getUser(String type, Object value) throws CustomException {
        Optional<User> findUser = null;
        if (type.equals("email")) {
            findUser = userRepository.findByUserEmail((String) value);
        } else if (type.equals("id")) {
            findUser = userRepository.findById((Long) value);
        }

        if (findUser == null || !findUser.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return findUser.get();
    }

    private ChatRoom getChatRoom_Id(Long chatRoomId) throws CustomException {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findById(chatRoomId);
        if(!findChatRoom.isPresent()) {
            throw new CustomException(ErrorCode.CHATROOM_NOT_FOUND);
        }
        return findChatRoom.get();
    }
    private ChatRoomRelation getChatRoomRelation(User findUser, ChatRoom findChatRoom) throws CustomException {
        Optional<ChatRoomRelation> findChatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(findUser, findChatRoom);
        if(!findChatRoomRelation.isPresent()) {
            throw new CustomException(ErrorCode.CHATROOM_RELATION_NOT_FOUND);
        }
        return findChatRoomRelation.get();
    }

    private void addResponseList(List<ChatRoomRequestDTO.ChatRoomUserDTO> requestList, List<ChatRoomResponseDTO.ChatRoomUserDTO> responseList, ChatRoom chatRoom) throws Exception {
        for(ChatRoomRequestDTO.ChatRoomUserDTO user : requestList) {
            User findUser = getUser("id", user.getUserId());
            ChatRoomRelation chatRoomRelation = chatRoomRelationRepository.save(new ChatRoomRelation(findUser, chatRoom));
            responseList.add(new ChatRoomResponseDTO.ChatRoomUserDTO(chatRoomRelation));
        }
    }

    private void checkRequestList(List<ChatRoomRequestDTO.ChatRoomUserDTO> requestList, Long findUserId) throws CustomException {
        for(ChatRoomRequestDTO.ChatRoomUserDTO user : requestList) {
            User findUser = getUser("id", user.getUserId());
            if(Objects.equals(findUser.getId(), findUserId)) {
                throw new CustomException(ErrorCode.CHAT_UNAVAILABLE);
            }
        }
    }
}
