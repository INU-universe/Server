package universe.universe.domain.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.Exception400;
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
            User findUser = getUser_Email(userEmail);
            ChatRoom findChatRoom = getChatRoom_Id(messageSaveDTO.getChatRoomId());
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
            User findUser = getUser_Email(userEmail);
            Message findMessage = getMessage_Id(messageId);
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
            User findUser = getUser_Email(userEmail);
            ChatRoom findChatRoom = getChatRoom_Id(chatRoomId);

            checkChatRoomRelation(findUser, findChatRoom);

            MessageResponseDTO.MessageFindAllDTO result = messageRepository.findAll(chatRoomId);
            return result;
        } catch (Exception e) {
            throw new Exception500("Message findAll fail : " + e.getMessage());
        }
    }

    private void checkChatRoomRelation(User findUser, ChatRoom findChatRoom) {
        Optional<ChatRoomRelation> findChatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(findUser, findChatRoom);
        if(!findChatRoomRelation.isPresent()) {
            throw new Exception400("chatRoomRelation", "해당 유저는 채팅방에서 찾을 수 없습니다.");
        }
    }

    private Message getMessage_Id(Long messageId) {
        Optional<Message> findMessage = messageRepository.findById(messageId);
        if(!findMessage.isPresent()) {
            throw new Exception400("messageId", "해당 메세지를 찾을 수 없습니다.");
        }
        return findMessage.get();
    }
    private ChatRoom getChatRoom_Id(Long chatRoomId) {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findById(chatRoomId);
        if(!findChatRoom.isPresent()) {
            throw new Exception400("chatRoomId", "해당 채팅방을 찾을 수 없습니다.");
        }
        return findChatRoom.get();
    }
    private User getUser_Email(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception400("userEmail", "해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }
    private User getUser_Id(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new Exception400("userId", "해당 유저를 찾을 수 없습니다.");
        }
        return findUser.get();
    }
}