package universe.universe.domain.message.repository;

import universe.universe.domain.message.dto.MessageResponseDTO;

public interface MessageRepositoryCustom {
    MessageResponseDTO.MessageFindAllDTO findAll(Long chatRoomId);
}
