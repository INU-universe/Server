package universe.universe.domain.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.domain.message.dto.MessageRequestDTO;
import universe.universe.domain.message.dto.MessageResponseDTO;
import universe.universe.domain.chatRoom.entity.ChatRoom;
import universe.universe.domain.chatRoomRelation.entity.ChatRoomRelation;
import universe.universe.domain.message.entity.Message;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.chatRoomRelation.repository.ChatRoomRelationRepository;
import universe.universe.domain.message.repository.MessageRepository;
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.CommonMethod;

import java.util.Objects;
import java.util.Optional;

import static universe.universe.global.common.reponse.ErrorCode.ACCESS_DENIED;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRelationRepository chatRoomRelationRepository;
    private final CommonMethod commonMethod;
    @Override
    public MessageResponseDTO.MessageSaveDTO save(MessageRequestDTO.MessageSaveDTO messageSaveDTO, String userEmail) {
        try {
            log.info("[MessageServiceImpl] save");
            User findUser = commonMethod.getUser("email", userEmail);
            ChatRoom findChatRoom = commonMethod.getChatRoom_Id(messageSaveDTO.getChatRoomId());
            String messageContent = messageSaveDTO.getContent();

            checkChatRoomRelation(findUser, findChatRoom);

            Message message = new Message(findUser, findChatRoom, messageContent);
            messageRepository.save(message);
            return new MessageResponseDTO.MessageSaveDTO(message);
        } catch (CustomException ce){
            log.info("[CustomException] MessageServiceImpl save");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] MessageServiceImpl save");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] MessageServiceImpl save : " + e.getMessage());
        }
    }

    @Override
    public void delete(String userEmail, Long messageId) {
        try {
            log.info("[MessageServiceImpl] delete");
            User findUser = commonMethod.getUser("email", userEmail);
            Message findMessage = commonMethod.getMessage_Id(messageId);
            if(!Objects.equals(findUser.getId(), findMessage.getUser().getId())) {
                throw new CustomException(ACCESS_DENIED);
            }
            messageRepository.delete(findMessage);
        } catch (CustomException ce){
            log.info("[CustomException] MessageServiceImpl delete");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] MessageServiceImpl save");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] MessageServiceImpl delete : " + e.getMessage());
        }
    }

    @Override
    public MessageResponseDTO.MessageFindAllDTO findAll(String userEmail, Long chatRoomId) {
        try {
            log.info("[MessageServiceImpl] findAll");
            User findUser = commonMethod.getUser("email", userEmail);
            ChatRoom findChatRoom = commonMethod.getChatRoom_Id(chatRoomId);

            checkChatRoomRelation(findUser, findChatRoom);

            return messageRepository.findAll(chatRoomId);
        } catch (CustomException ce){
            log.info("[CustomException] MessageServiceImpl findAll");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] MessageServiceImpl findAll");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] MessageServiceImpl findAll : " + e.getMessage());
        }
    }

    private void checkChatRoomRelation(User findUser, ChatRoom findChatRoom) throws CustomException {
        Optional<ChatRoomRelation> findChatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(findUser, findChatRoom);
        if(!findChatRoomRelation.isPresent()) {
            throw new CustomException(ErrorCode.CHATROOM_RELATION_NOT_FOUND);
        }
    }
}