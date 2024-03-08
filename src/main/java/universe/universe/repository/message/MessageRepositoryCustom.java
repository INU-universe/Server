package universe.universe.repository.message;

import universe.universe.dto.message.MessageResponseDTO;

public interface MessageRepositoryCustom {
    MessageResponseDTO.MessageFindAllDTO findAll(Long chatRoomId);
}
