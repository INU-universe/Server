package universe.universe.domain.friendRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import universe.universe.domain.friendRequest.entity.FriendRequest;
import universe.universe.domain.user.entity.User;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>, FriendRequestRepositoryCustom {
    Optional<FriendRequest> findByFromUserAndToUser(User fromUser, User toUser);
}
