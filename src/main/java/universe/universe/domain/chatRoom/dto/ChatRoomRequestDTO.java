package universe.universe.domain.chatRoom.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChatRoomRequestDTO {
    @Setter
    @Getter
    public static class ChatRoomCreateDTO {
        private List<ChatRoomUserDTO> userList;
    }

    @Setter
    @Getter
    public static class ChatRoomUserDTO {
        private Long userId;
    }
}
