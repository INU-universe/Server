package universe.universe.repository.chatRoom;

import universe.universe.dto.chatRoom.ChatRoomResponseDTO;
import universe.universe.dto.friend.FriendResponseDTO;

public interface ChatRoomRelationRepositoryCustom {
    ChatRoomResponseDTO.ChatRoomFindAllDTO ChatRoomRelationFindAll(Long userId);
}
