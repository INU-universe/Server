package universe.universe.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.message.MessageRequestDTO;
import universe.universe.dto.message.MessageResponseDTO;
import universe.universe.entitiy.chatRoom.ChatRoom;
import universe.universe.entitiy.chatRoom.ChatRoomRelation;
import universe.universe.entitiy.message.Message;
import universe.universe.entitiy.user.User;
import universe.universe.repository.chatRoom.ChatRoomRelationRepository;
import universe.universe.repository.chatRoom.ChatRoomRepository;
import universe.universe.repository.message.MessageRepository;
import universe.universe.repository.user.UserRepository;

import java.util.Objects;
import java.util.Optional;

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
            User findUser = getUserEmail(userEmail);
            ChatRoom findChatRoom = getChatRoomId(messageSaveDTO.getChatRoomId());
            String messageContent = messageSaveDTO.getContent();

            checkChatRoomRelation(findUser, findChatRoom);

            Message message = new Message(findUser, findChatRoom, messageContent);
            messageRepository.save(message);
            return new MessageResponseDTO.MessageSaveDTO(message);
        } catch (Exception e) {
            throw new Exception500("Message save fail : " + e.getMessage());
        }
    }

    @Override
    public void delete(String userEmail, Long messageId) {
        try {
            User findUser = getUserEmail(userEmail);
            Message findMessage = getMessageId(messageId);
            if(!Objects.equals(findUser.getId(), findMessage.getUser().getId())) {
                throw new Exception400("userEmail", "회원이 맞지 않습니다.");
            }
            messageRepository.delete(findMessage);
        } catch (Exception e) {
            throw new Exception500("Message delete fail : " + e.getMessage());
        }
    }

    @Override
    public MessageResponseDTO.MessageFindAllDTO findAll(String userEmail, Long chatRoomId) {
        try {
            User findUser = getUserEmail(userEmail);
            ChatRoom findChatRoom = getChatRoomId(chatRoomId);

            checkChatRoomRelation(findUser, findChatRoom);

            MessageResponseDTO.MessageFindAllDTO messageFindAllDTO = messageRepository.findAll(chatRoomId);
            return messageFindAllDTO;
        } catch (Exception e) {
            throw new Exception500("Message findAll fail : " + e.getMessage());
        }
    }

    private void checkChatRoomRelation(User findUser, ChatRoom findChatRoom) {
        Optional<ChatRoomRelation> findChatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(findUser, findChatRoom);
        if(!findChatRoomRelation.isPresent()) {
            throw new Exception404("해당 유저는 채팅방에서 찾을 수 없습니다.");
        }
    }

    private Message getMessageId(Long messageId) {
        Optional<Message> findMessage = messageRepository.findById(messageId);
        if(!findMessage.isPresent()) {
            throw new Exception404("해당 메세지를 찾을 수 없습니다.");
        }
        return findMessage.get();
    }
    private ChatRoom getChatRoomId(Long chatRoomId) {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findById(chatRoomId);
        if(!findChatRoom.isPresent()) {
            throw new Exception404("해당 채팅방을 찾을 수 없습니다.");
        }
        return findChatRoom.get();
    }
    private User getUserEmail(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }
    private User getUserId(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser.get();
    }
}