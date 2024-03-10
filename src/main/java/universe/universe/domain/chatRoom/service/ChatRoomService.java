package universe.universe.domain.chatRoom.service;

import universe.universe.domain.chatRoom.dto.ChatRoomRequestDTO;
import universe.universe.domain.chatRoom.dto.ChatRoomResponseDTO;

public interface ChatRoomService {
    ChatRoomResponseDTO.ChatRoomCreateDTO create(String userEmail, ChatRoomRequestDTO.ChatRoomCreateDTO chatRoomCreateDTO);
    void delete(String userEmail, Long chatRoomId);
    ChatRoomResponseDTO.ChatRoomFindAllDTO findAll(String userEmail);
}
