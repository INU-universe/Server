package universe.universe.repository.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.friend.FriendRequest;
import universe.universe.entitiy.user.User;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findByFromUserAndToUser(User fromUser, User toUser);
}
