package universe.universe.dto.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import universe.universe.entitiy.friend.Friend;
import universe.universe.entitiy.friend.FriendStatus;
import universe.universe.entitiy.message.Message;
import universe.universe.entitiy.user.User;
import universe.universe.entitiy.user.UserStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessageResponseDTO {
    @Getter
    @Setter
    public static class MessageSaveDTO {
        private Long messageId;
        private Long useId;
        private Long chatRoomId;
        private String content;
        private LocalDateTime messageTime;
        public MessageSaveDTO(Message message) {
            this.messageId = message.getId();
            this.useId = message.getUser().getId();
            this.chatRoomId = message.getChatRoom().getId();
            this.content = message.getContent();
            this.messageTime = message.getCreatedTime();
        }
    }
    @Getter
    @Setter
    public static class MessageFindOneDTO {
        private Long messageId;
        private Long useId;
        private String content;
        private LocalDateTime messageTime;

        public MessageFindOneDTO() {
        }
        public MessageFindOneDTO(Long messageId, Long useId, String content, LocalDateTime messageTime) {
            this.messageId = messageId;
            this.useId = useId;
            this.content = content;
            this.messageTime = messageTime;
        }
    }
    @Getter
    @Setter
    public static class MessageFindAllDTO {
        private Long chatRoomId;
        private List<MessageResponseDTO.MessageFindOneDTO> messageList;

        public MessageFindAllDTO(Long chatRoomId, List<MessageFindOneDTO> messageList) {
            this.chatRoomId = chatRoomId;
            this.messageList = messageList;
        }
    }
}
