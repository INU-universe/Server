package universe.universe.repository.chatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.chatRoom.ChatRoom;
import universe.universe.entitiy.chatRoom.ChatRoomRelation;
import universe.universe.entitiy.friend.FriendRequest;
import universe.universe.entitiy.user.User;

import java.util.Optional;

public interface ChatRoomRelationRepository extends JpaRepository<ChatRoomRelation, Long>, ChatRoomRelationRepositoryCustom {
    Optional<ChatRoomRelation> findByUserAndChatRoom(User user, ChatRoom chatRoom);
}
