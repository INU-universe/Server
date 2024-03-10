package universe.universe.domain.message.service;

import universe.universe.domain.message.dto.MessageRequestDTO;
import universe.universe.domain.message.dto.MessageResponseDTO;

public interface MessageService {
    // 메세지 저장
    MessageResponseDTO.MessageSaveDTO save(MessageRequestDTO.MessageSaveDTO messageSaveDTO, String userEmail);

    // 메세지 삭제
    void delete(String userEmail, Long messageId);

    // 메세지 전체 조회
    MessageResponseDTO.MessageFindAllDTO findAll(String userEmail, Long chatRoomId);
}
