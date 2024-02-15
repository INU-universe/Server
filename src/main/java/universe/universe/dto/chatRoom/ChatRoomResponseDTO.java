package universe.universe.dto.chatRoom;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.chatRoom.ChatRoomRelation;
import java.util.List;

@Data
public class ChatRoomResponseDTO {
    @Setter
    @Getter
    public static class ChatRoomUserListFindDTO {
        private Long userId;
        private String userEmail;
        public ChatRoomUserListFindDTO(ChatRoomRelation chatRoomRelation) {
            this.userId = chatRoomRelation.getUser().getId();
            this.userEmail = chatRoomRelation.getUser().getUserEmail();
        }
    }

    @Setter
    @Getter
    public static class ChatRoomRelationCreateDTO {
        private Long chatRoomId;
        private List<ChatRoomUserListFindDTO> userList;
        public ChatRoomRelationCreateDTO(Long chatRoomId, List<ChatRoomUserListFindDTO> userList) {
            this.chatRoomId = chatRoomId;
            this.userList = userList;
        }
    }
}
