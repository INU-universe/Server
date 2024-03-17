package universe.universe.domain.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.exception.Exception500;
import universe.universe.domain.message.dto.MessageRequestDTO;
import universe.universe.domain.message.dto.MessageResponseDTO;
import universe.universe.domain.chatRoom.entity.ChatRoom;
import universe.universe.domain.chatRoomRelation.entity.ChatRoomRelation;
import universe.universe.domain.message.entity.Message;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.chatRoomRelation.repository.ChatRoomRelationRepository;
import universe.universe.domain.chatRoom.repository.ChatRoomRepository;
import universe.universe.domain.message.repository.MessageRepository;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.reponse.ErrorCode;

import java.util.Objects;
import java.util.Optional;

import static universe.universe.global.common.reponse.ErrorCode.ACCESS_DENIED;
import static universe.universe.global.common.reponse.ErrorCode.MESSAGE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    final private MessageRepository messageRepository;
    final private UserRepository userRepository;
    final private ChatRoomRepository chatRoomRepository;
    final private ChatRoomRelationRepository chatRoomRelationRepository;
    @Override
    public MessageResponseDTO.MessageSaveDTO save(MessageRequestDTO.MessageSaveDTO messageSaveDTO, String userEmail) {
        try {
            log.info("[MessageServiceImpl] save");
            User findUser = getUser("email", userEmail);
            ChatRoom findChatRoom = getChatRoom_Id(messageSaveDTO.getChatRoomId());
            String messageContent = messageSaveDTO.getContent();

            checkChatRoomRelation(findUser, findChatRoom);

            Message message = new Message(findUser, findChatRoom, messageContent);
            messageRepository.save(message);
            return new MessageResponseDTO.MessageSaveDTO(message);
        } catch (CustomException ce){
            log.info("[CustomException] MessageServiceImpl save");
            throw ce;
        } catch (Exception e) {
            throw new Exception500("MessageServiceImpl save fail : " + e.getMessage());
        }
    }

    @Override
    public void delete(String userEmail, Long messageId) {
        try {
            log.info("[MessageServiceImpl] delete");
            User findUser = getUser("email", userEmail);
            Message findMessage = getMessage_Id(messageId);
            if(!Objects.equals(findUser.getId(), findMessage.getUser().getId())) {
                throw new CustomException(ACCESS_DENIED);
            }
            messageRepository.delete(findMessage);
        } catch (CustomException ce){
            log.info("[CustomException] MessageServiceImpl delete");
            throw ce;
        } catch (Exception e) {
            throw new Exception500("MessageServiceImpl delete fail : " + e.getMessage());
        }
    }

    @Override
    public MessageResponseDTO.MessageFindAllDTO findAll(String userEmail, Long chatRoomId) {
        try {
            log.info("[MessageServiceImpl] findAll");
            User findUser = getUser("email", userEmail);
            ChatRoom findChatRoom = getChatRoom_Id(chatRoomId);

            checkChatRoomRelation(findUser, findChatRoom);

            MessageResponseDTO.MessageFindAllDTO result = messageRepository.findAll(chatRoomId);
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] MessageServiceImpl findAll");
            throw ce;
        } catch (Exception e) {
            throw new Exception500("MessageServiceImpl findAll fail : " + e.getMessage());
        }
    }

    private void checkChatRoomRelation(User findUser, ChatRoom findChatRoom) throws CustomException {
        Optional<ChatRoomRelation> findChatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(findUser, findChatRoom);
        if(!findChatRoomRelation.isPresent()) {
            throw new CustomException(ErrorCode.CHATROOM_RELATION_NOT_FOUND);
        }
    }

    private Message getMessage_Id(Long messageId) throws CustomException {
        Optional<Message> findMessage = messageRepository.findById(messageId);
        if(!findMessage.isPresent()) {
            throw new CustomException(MESSAGE_NOT_FOUND);
        }
        return findMessage.get();
    }
    private ChatRoom getChatRoom_Id(Long chatRoomId) throws CustomException {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findById(chatRoomId);
        if(!findChatRoom.isPresent()) {
            throw new CustomException(ErrorCode.CHATROOM_NOT_FOUND);
        }
        return findChatRoom.get();
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
}