package universe.universe.service.chatRoom;

import universe.universe.dto.chatRoom.ChatRoomRequestDTO;
import universe.universe.dto.chatRoom.ChatRoomResponseDTO;

public interface ChatRoomService {
    ChatRoomResponseDTO.ChatRoomCreateDTO create(String userEmail, ChatRoomRequestDTO.ChatRoomCreateDTO chatRoomCreateDTO);
    void delete(String userEmail, Long chatRoomId);
    ChatRoomResponseDTO.ChatRoomFindAllDTO findAll(String userEmail);
}
