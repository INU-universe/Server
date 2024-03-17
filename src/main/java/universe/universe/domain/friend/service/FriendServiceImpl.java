package universe.universe.domain.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.exception.Exception500;
import universe.universe.domain.friend.dto.FriendResponseDTO;
import universe.universe.domain.friend.entity.Friend;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.friend.repository.FriendRepository;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.reponse.ErrorCode;

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
            log.info("[FriendServiceImpl] findAll");
            User findUser = getUser_Email(userEmail);
            FriendResponseDTO.FriendFindAllDTO result = friendRepository.findAll(findUser.getId());
            return result;
        } catch (Exception e) {
            throw new Exception500("friend findAll fail : " + e.getMessage());
        }
    }

    @Override
    public FriendResponseDTO.FriendFindInSchoolDTO findInSchool(String userEmail) {
        try {
            log.info("[FriendServiceImpl] findInSchool");
            User findUser = getUser_Email(userEmail);
            FriendResponseDTO.FriendFindInSchoolDTO result = friendRepository.findInSchool(findUser.getId());
            return result;
        } catch (Exception e) {
            throw new Exception500("friend findInSchool fail : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(String userEmail, Long userId) {
        try {
            log.info("[FriendServiceImpl] delete");
            Result result = getFriend(userEmail, userId);

            friendRepository.delete(result.findRelation1.get());
            friendRepository.delete(result.findRelation2.get());
        } catch (Exception e) {
            throw new Exception500("friend delete fail : " + e.getMessage());
        }
    }

//    @Override
//    @Transactional
//    public FriendResponseDTO.FriendToggleDTO toggle(String userEmail, Long userId) {
//        try {
//            Result result = getFriend(userEmail, userId);
//
//            if(result.findRelation1().get().getFriendStatus() == FriendStatus.FAVORITE && result.findRelation2().get().getFriendStatus() == FriendStatus.FAVORITE) {
//                result.findRelation1().get().updateFriendStatus(FriendStatus.NOT_FAVORITE);
//                result.findRelation2().get().updateFriendStatus(FriendStatus.NOT_FAVORITE);
//            }
//            else if (result.findRelation1().get().getFriendStatus() == FriendStatus.NOT_FAVORITE && result.findRelation2().get().getFriendStatus() == FriendStatus.NOT_FAVORITE) {
//                result.findRelation1().get().updateFriendStatus(FriendStatus.FAVORITE);
//                result.findRelation2().get().updateFriendStatus(FriendStatus.FAVORITE);
//            }
//            else {
//                throw new Exception404("해당 관계에 문제가 있습니다.");
//            }
//            return new FriendResponseDTO.FriendToggleDTO(result.findRelation1().get());
//
//        } catch (Exception e) {
//            throw new Exception500("toggle fail : " + e.getMessage());
//        }
//    }

    private Result getFriend(String userEmail, Long userId) throws Exception {
        User fromUser = getUser_Email(userEmail);
        User toUser = getUser_Id(userId);
        Optional<Friend> findRelation1 = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        Optional<Friend> findRelation2 = friendRepository.findByFromUserAndToUser(toUser, fromUser);

        if(!findRelation1.isPresent() || !findRelation2.isPresent()) {
            throw new CustomException(ErrorCode.FRIEND_NOT_FOUND);
        }
        Result result = new Result(findRelation1, findRelation2);
        return result;
    }

    private record Result(Optional<Friend> findRelation1, Optional<Friend> findRelation2) {
    }

    private User getUser_Email(String userEmail) throws Exception {
        Optional<User> findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return findUser.get();
    }

    private User getUser_Id(Long userId) throws Exception {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return findUser.get();
    }
}