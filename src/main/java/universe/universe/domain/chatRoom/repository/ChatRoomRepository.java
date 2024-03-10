package universe.universe.domain.chatRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.domain.chatRoom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
