package universe.universe.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.friend.FriendResponseDTO;
import universe.universe.entitiy.friend.Friend;
import universe.universe.entitiy.friend.FriendStatus;
import universe.universe.entitiy.user.User;
import universe.universe.repository.friend.FriendRepository;
import universe.universe.repository.user.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl implements FriendService {
    final private UserRepository userRepository;
    final private FriendRepository friendRepository;
    @Override
    public FriendResponseDTO.FriendFindAllDTO findAll(String userEmail) {
        try {
            User findUser = getUser(userEmail);
            FriendResponseDTO.FriendFindAllDTO findFriendList = friendRepository.findAll(findUser.getId());
            return findFriendList;
        } catch (Exception e) {
            throw new Exception500("findAll fail : " + e.getMessage());
        }
    }

    @Override
    public FriendResponseDTO.FriendFindInSchoolDTO findInSchool(String userEmail) {
        try {
            User findUser = getUser(userEmail);
            FriendResponseDTO.FriendFindInSchoolDTO friendFindInSchoolList = friendRepository.findInSchool(findUser.getId());
            return friendFindInSchoolList;
        } catch (Exception e) {
            throw new Exception500("findInSchool fail : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(String userEmail, Long userId) {
        try {
            Result result = getFriend(userEmail, userId);

            friendRepository.delete(result.findRelation1.get());
            friendRepository.delete(result.findRelation2.get());
        } catch (Exception e) {
            throw new Exception500("delete fail : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public FriendResponseDTO.FriendToggleDTO toggle(String userEmail, Long userId) {
        try {
            Result result = getFriend(userEmail, userId);

            if(result.findRelation1().get().getFriendStatus() == FriendStatus.FAVORITE && result.findRelation2().get().getFriendStatus() == FriendStatus.FAVORITE) {
                result.findRelation1().get().updateFriendStatus(FriendStatus.NOT_FAVORITE);
                result.findRelation2().get().updateFriendStatus(FriendStatus.NOT_FAVORITE);
            }
            else if (result.findRelation1().get().getFriendStatus() == FriendStatus.NOT_FAVORITE && result.findRelation2().get().getFriendStatus() == FriendStatus.NOT_FAVORITE) {
                result.findRelation1().get().updateFriendStatus(FriendStatus.FAVORITE);
                result.findRelation2().get().updateFriendStatus(FriendStatus.FAVORITE);
            }
            else {
                throw new Exception404("해당 관계에 문제가 있습니다.");
            }
            return new FriendResponseDTO.FriendToggleDTO(result.findRelation1().get());

        } catch (Exception e) {
            throw new Exception500("toggle fail : " + e.getMessage());
        }
    }

    private Result getFriend(String userEmail, Long userId) {
        User fromUser = getUser(userEmail);
        User toUser = getUserId(userId);
        Optional<Friend> findRelation1 = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        Optional<Friend> findRelation2 = friendRepository.findByFromUserAndToUser(toUser, fromUser);

        if(!findRelation1.isPresent() || !findRelation2.isPresent()) {
            throw new Exception404("해당 관계를 찾을 수 없습니다.");
        }
        Result result = new Result(findRelation1, findRelation2);
        return result;
    }

    private record Result(Optional<Friend> findRelation1, Optional<Friend> findRelation2) {
    }

    private User getUser(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }
    private User getUserId(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser.get();
    }
}