package universe.universe.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception403;
import universe.universe.common.exception.Exception404;
import universe.universe.dto.friend.FriendRelationShipResponseDTO;
import universe.universe.entitiy.friend.FriendRelationShip;
import universe.universe.entitiy.user.User;
import universe.universe.repository.friend.FriendRelationShipRepository;
import universe.universe.repository.user.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FriendRelationShipServiceImpl implements FriendRelationShipService {
    final private UserRepository userRepository;
    final private FriendRelationShipRepository friendRelationShipRepository;
    @Override
    @Transactional
    public FriendRelationShipResponseDTO.FriendRelationShipToggleDTO toggle(String userEmail, Long toUserId) {
        User fromUser = getUser(userEmail);
        Optional<User> findToUser = userRepository.findById(toUserId);
        if(findToUser.isEmpty()) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        else if(Objects.equals(fromUser.getId(), toUserId)) {
            throw new Exception403("본인에게 친구 추가는 불가합니다.");
        }
        User toUser = findToUser.get();

        Optional<FriendRelationShip> findExist = friendRelationShipRepository.findByFromUserAndToUser(fromUser, toUser);

        if(findExist.isPresent()) {
            FriendRelationShip deleteFindExist = findExist.get();
            friendRelationShipRepository.delete(deleteFindExist);
            return null;
        }
        else {
            FriendRelationShip friendRelationShip = new FriendRelationShip(fromUser, toUser);
            friendRelationShipRepository.save(friendRelationShip);
            return new FriendRelationShipResponseDTO.FriendRelationShipToggleDTO(friendRelationShip);
        }
    }

    private User getUser(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }
}
