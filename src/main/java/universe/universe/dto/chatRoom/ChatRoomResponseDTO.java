package universe.universe.dto.chatRoom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.chatRoom.ChatRoomRelation;
import java.util.List;

@Setter
@Getter
public class ChatRoomResponseDTO {
    @Setter
    @Getter
    public static class ChatRoomUserDTO {
        private Long userId;
        private String userEmail;
        public ChatRoomUserDTO(ChatRoomRelation chatRoomRelation) {
            this.userId = chatRoomRelation.getUser().getId();
            this.userEmail = chatRoomRelation.getUser().getUserEmail();
        }
    }

    @Setter
    @Getter
    public static class ChatRoomCreateDTO {
        private Long chatRoomId;
        private List<ChatRoomUserDTO> userList;
        public ChatRoomCreateDTO(Long chatRoomId, List<ChatRoomUserDTO> userList) {
            this.chatRoomId = chatRoomId;
            this.userList = userList;
        }
    }
    @Setter
    @Getter
    @AllArgsConstructor
    public static class ChatRoomUserInfoDTO {
        private Long userId;
        private String userName;
        private String userImg;
        private Long chatRoomRelationId;
    }
    @Setter
    @Getter
    @AllArgsConstructor
    public static class ChatRoomFindOneDTO {
        private Long chatRoomId;
        private List<ChatRoomUserInfoDTO> userList;
    }
    @Setter
    @Getter
    public static class ChatRoomFindAllDTO {
        private Long userId;
        private List<ChatRoomFindOneDTO> chatRoomList;

        public ChatRoomFindAllDTO(Long userId, List<ChatRoomFindOneDTO> chatRoomList) {
            this.userId = userId;
            this.chatRoomList = chatRoomList;
        }
    }
}
