package universe.universe.repository.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.friend.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
