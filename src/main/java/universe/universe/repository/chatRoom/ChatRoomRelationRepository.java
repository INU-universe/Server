package universe.universe.repository.chatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.chatRoom.ChatRoomRelation;

public interface ChatRoomRelationRepository extends JpaRepository<ChatRoomRelation, Long> {
}
