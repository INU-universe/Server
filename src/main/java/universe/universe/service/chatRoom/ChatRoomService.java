package universe.universe.service.chatRoom;

import universe.universe.dto.chatRoom.ChatRoomRequestDTO;
import universe.universe.dto.chatRoom.ChatRoomResponseDTO;

public interface ChatRoomService {
    ChatRoomResponseDTO.ChatRoomRelationCreateDTO create(ChatRoomRequestDTO.ChatRoomRelationCreateDTO chatRoomRelationCreateDTO);
}
