package universe.universe.domain.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.domain.friend.entity.Friend;
import universe.universe.domain.user.entity.User;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendRepositoryCustom {
    Optional<Friend> findByFromUserAndToUser(User fromUser, User toUser);
}
