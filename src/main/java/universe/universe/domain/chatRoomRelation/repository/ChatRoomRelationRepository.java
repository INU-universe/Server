package universe.universe.domain.chatRoomRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.domain.chatRoom.entity.ChatRoom;
import universe.universe.domain.chatRoomRelation.entity.ChatRoomRelation;
import universe.universe.domain.user.entity.User;

import java.util.Optional;

public interface ChatRoomRelationRepository extends JpaRepository<ChatRoomRelation, Long>, ChatRoomRelationRepositoryCustom {
    Optional<ChatRoomRelation> findByUserAndChatRoom(User user, ChatRoom chatRoom);
}
