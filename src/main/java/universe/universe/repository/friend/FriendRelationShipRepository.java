package universe.universe.repository.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.entitiy.friend.FriendRelationShip;
import universe.universe.entitiy.user.User;

import java.util.Optional;

public interface FriendRelationShipRepository extends JpaRepository<FriendRelationShip, Long> {
    Optional<FriendRelationShip> findByFromUserAndToUser(User fromUser, User toUser);
}
