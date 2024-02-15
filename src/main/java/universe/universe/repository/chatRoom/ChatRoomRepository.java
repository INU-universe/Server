package universe.universe.repository.chatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.chatRoom.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
