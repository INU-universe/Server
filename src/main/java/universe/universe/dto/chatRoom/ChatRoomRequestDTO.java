package universe.universe.dto.chatRoom;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ChatRoomRequestDTO {
    @Setter
    @Getter
    public static class ChatRoomRelationCreateDTO {
        private List<ChatRoomRelationUserCreateDTO> userList;
    }

    @Setter
    @Getter
    public static class ChatRoomRelationUserCreateDTO {
        private Long userId;
    }
}
