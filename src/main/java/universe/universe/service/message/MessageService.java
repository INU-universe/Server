package universe.universe.service.message;

import universe.universe.dto.message.MessageRequestDTO;
import universe.universe.dto.message.MessageResponseDTO;

public interface MessageService {
    // 메세지 저장
    MessageResponseDTO.MessageSaveDTO save(MessageRequestDTO.MessageSaveDTO messageSaveDTO);

    // 메세지 삭제
    void delete(String userEmail, Long messageId);

    // 메세지 전체 조회
    MessageResponseDTO.MessageFindAllDTO findAll(String userEmail, Long chatRoomId);
}
