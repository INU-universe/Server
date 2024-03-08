package universe.universe.dto.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessageRequestDTO {
    @Getter
    @Setter
    public static class MessageSaveDTO {
        private Long chatRoomId;
        private Long userId;
        private String content;
    }
}
