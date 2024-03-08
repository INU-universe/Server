package universe.universe.dto.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.message.Message;
import universe.universe.entitiy.user.User;

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
    }
    @Getter
    @Setter
    public static class MessageFindAllDTO {
        private Long chatRoomId;
        private List<MessageResponseDTO.MessageFindOneDTO> messageList;
    }
}
