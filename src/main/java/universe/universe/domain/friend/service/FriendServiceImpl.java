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
            return friendRepository.findAll(findUser.getId());
        } catch (CustomException ce){
            log.info("[CustomException] FriendServiceImpl findAll");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] FriendServiceImpl findAll");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] FriendServiceImpl findOne : " + e.getMessage());
        }
    }

    @Override
    public FriendResponseDTO.FriendFindInSchoolDTO findInSchool(String userEmail) {
        try {
            log.info("[FriendServiceImpl] findInSchool");
            User findUser = commonMethod.getUser("email", userEmail);
            return friendRepository.findInSchool(findUser.getId());
        } catch (CustomException ce){
            log.info("[CustomException] FriendServiceImpl findInSchool");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] FriendServiceImpl findInSchool");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] FriendServiceImpl findInSchool : " + e.getMessage());
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
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] FriendServiceImpl delete : " + e.getMessage());
        }
    }

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