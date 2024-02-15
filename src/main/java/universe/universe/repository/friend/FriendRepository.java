package universe.universe.repository.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.friend.Friend;
import universe.universe.entitiy.user.User;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendRepositoryCustom {
    Optional<Friend> findByFromUserAndToUser(User fromUser, User toUser);
}
