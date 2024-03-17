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
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.CommonMethod;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final CommonMethod commonMethod;
    @Override
    public FriendResponseDTO.FriendFindAllDTO findAll(String userEmail) {
        try {
            log.info("[FriendServiceImpl] findAll");
            User findUser = commonMethod.getUser("email", userEmail);
            FriendResponseDTO.FriendFindAllDTO result = friendRepository.findAll(findUser.getId());
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] FriendServiceImpl findAll");
            throw ce;
        } catch (Exception e) {

            throw new CustomException(ErrorCode.SERVER_ERROR, "FriendServiceImpl findAll : " + e.getMessage());
        }
    }

    @Override
    public FriendResponseDTO.FriendFindInSchoolDTO findInSchool(String userEmail) {
        try {
            log.info("[FriendServiceImpl] findInSchool");
            User findUser = commonMethod.getUser("email", userEmail);
            FriendResponseDTO.FriendFindInSchoolDTO result = friendRepository.findInSchool(findUser.getId());
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] FriendServiceImpl findInSchool");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] FriendServiceImpl findInSchool");
            throw new Exception500("FriendServiceImpl findInSchool fail : " + e.getMessage());
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
        } catch (CustomException ce){
            log.info("[CustomException] FriendServiceImpl delete");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] FriendServiceImpl delete");
            throw new Exception500("FriendServiceImpl findInSchool delete : " + e.getMessage());
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

    public record Result(Optional<Friend> findRelation1, Optional<Friend> findRelation2) {
    }

    public FriendServiceImpl.Result getFriend(String userEmail, Long userId) throws CustomException {
        User fromUser = commonMethod.getUser("email", userEmail);
        User toUser = commonMethod.getUser("id", userId);
        Optional<Friend> findRelation1 = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        Optional<Friend> findRelation2 = friendRepository.findByFromUserAndToUser(toUser, fromUser);

        if(!findRelation1.isPresent() || !findRelation2.isPresent()) {
            throw new CustomException(ErrorCode.FRIEND_NOT_FOUND);
        }
        FriendServiceImpl.Result result = new FriendServiceImpl.Result(findRelation1, findRelation2);
        return result;
    }
}