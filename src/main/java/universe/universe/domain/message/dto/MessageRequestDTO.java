package universe.universe.domain.message.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessageRequestDTO {
    @Getter
    @Setter
    public static class MessageSaveDTO {
        private Long chatRoomId;
        private String content;
    }
}
