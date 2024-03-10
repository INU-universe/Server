package universe.universe.domain.chatRoomRelation.repository;

import universe.universe.domain.chatRoom.dto.ChatRoomResponseDTO;

public interface ChatRoomRelationRepositoryCustom {
    ChatRoomResponseDTO.ChatRoomFindAllDTO ChatRoomRelationFindAll(Long userId);
}
