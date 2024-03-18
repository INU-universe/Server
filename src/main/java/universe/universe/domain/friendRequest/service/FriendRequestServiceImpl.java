package universe.universe.domain.friendRequest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.auth.jwt.JwtProperties;
import universe.universe.global.common.exception.CustomException;
import universe.universe.domain.friendRequest.dto.FriendRequestResponseDTO;
import universe.universe.domain.friend.entity.Friend;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.friend.repository.FriendRepository;
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.CommonMethod;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static universe.universe.domain.friend.entity.FriendStatus.NOT_FAVORITE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRepository friendRepository;
    private final CommonMethod commonMethod;
    @Value(("${jwt.secret}"))
    private String secretKey;

    @Override
    public FriendRequestResponseDTO.FriendRequestGetURLDTO getURL(String userEmail) {
        try {
            log.info("[FriendRequestServiceImpl] getURL");
            User findUser = commonMethod.getUser("email", userEmail);
            String token = JWT.create()
                    .withSubject("accessToken")
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                    .withClaim("userId", findUser.getId())
                    .sign(Algorithm.HMAC512(secretKey)); // 고유한 값

            String friendRequestURL = "http://universe.greenecho.shop:8080/api/v1/user/friendRequest/accept?token=" + token;

            FriendRequestResponseDTO.FriendRequestGetURLDTO result = new FriendRequestResponseDTO.FriendRequestGetURLDTO();
            result.setFriendRequestURL(friendRequestURL);
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] FriendRequestServiceImpl getURL");
            throw ce;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "FriendRequestServiceImpl send : " + e.getMessage());
        }
    }

    @Override
    public FriendRequestResponseDTO.FriendRequestAcceptURLDTO acceptURL(String userEmail, String token) {
        try {
            log.info("[FriendRequestServiceImpl] acceptURL");
            Long fromUserId = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token).getClaim("userId").asLong();
            User fromUser = commonMethod.getUser("id", fromUserId);
            User toUser = commonMethod.getUser("email", userEmail);

            Optional<Friend> friendExist = friendRepository.findByFromUserAndToUser(fromUser, toUser);

            if(Objects.equals(fromUser.getId(), toUser.getId())) {
                throw new CustomException(ErrorCode.FRIEND_UNAVAILABLE);
            }

            if(friendExist.isPresent()) {
                throw new CustomException(ErrorCode.FRIEND_EXIST);
            }
            else {
                Friend friend1 = new Friend(fromUser, toUser, NOT_FAVORITE);
                Friend friend2 = new Friend(toUser, fromUser, NOT_FAVORITE);
                friendRepository.save(friend1);
                friendRepository.save(friend2);
                return new FriendRequestResponseDTO.FriendRequestAcceptURLDTO(fromUser, toUser);
            }
        } catch (CustomException ce){
            log.info("[CustomException] FriendRequestServiceImpl acceptURL");
            throw ce;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "FriendRequestServiceImpl acceptURL : " + e.getMessage());
        }
    }

    /** 친구 토글 안 쓸 예정 **/
//    @Override
//    public FriendRequestResponseDTO.FriendRequestToggleDTO toggle(String userEmail, Long toUserId) {
//        try {
//            User fromUser = getUser(userEmail);
//            Optional<User> findToUser = userRepository.findById(toUserId);
//            if(findToUser.isEmpty()) {
//                throw new Exception404("해당 유저를 찾을 수 없습니다.");
//            }
//            else if(Objects.equals(fromUser.getId(), toUserId)) {
//                throw new Exception401("본인에게 친구 추가는 불가합니다.");
//            }
//            User toUser = findToUser.get();
//
//            Optional<FriendRequest> friendRequestExist = friendRequestRepository.findByFromUserAndToUser(fromUser, toUser);
//            Optional<Friend> friendExist = friendRepository.findByFromUserAndToUser(fromUser, toUser);
//
//            if(friendExist.isPresent()) {
//                return null;
//            }
//            else if(friendRequestExist.isPresent()) {
//                FriendRequest deleteFindExist = friendRequestExist.get();
//                friendRequestRepository.delete(deleteFindExist);
//                return null;
//            }
//            else {
//                FriendRequest friendRequest = new FriendRequest(fromUser, toUser);
//                friendRequestRepository.save(friendRequest);
//                return new FriendRequestResponseDTO.FriendRequestToggleDTO(friendRequest);
//            }
//        } catch (Exception e) {
//            throw new Exception500("toggle fail : " + e.getMessage());
//        }
//    }
//
//    @Override
//    public FriendRequestResponseDTO.FriendRequestAcceptDTO accept(String userEmail, Long friendRelationShipId) {
//        try {
//            User findUser = getUser(userEmail);
//            Optional<FriendRequest> findExist = friendRequestRepository.findById(friendRelationShipId);
//            if(!Objects.equals(findUser.getId(), findExist.get().getToUser().getId())) {
//                throw new Exception401("권한이 없습니다.");
//            }
//
//            if(findExist.isPresent()) {
//                FriendRequest findFriendRequest = findExist.get();
//                Friend friend1 = new Friend(findFriendRequest.getFromUser(), findFriendRequest.getToUser(), NOT_FAVORITE);
//                Friend friend2 = new Friend(findFriendRequest.getToUser(), findFriendRequest.getFromUser(), NOT_FAVORITE);
//                friendRepository.save(friend1);
//                friendRepository.save(friend2);
//                friendRequestRepository.delete(findFriendRequest);
//                return new FriendRequestResponseDTO.FriendRequestAcceptDTO(findFriendRequest);
//            }
//            else {
//                throw new Exception404("해당 관계를 찾을 수 없습니다.");
//            }
//        } catch (Exception e) {
//            throw new Exception500("accept fail : " + e.getMessage());
//        }
//    }
//
//    @Override
//    public FriendRequestResponseDTO.FriendRequestRejectDTO reject(String userEmail, Long friendRelationShipId) {
//        try {
//            User findUser = getUser(userEmail);
//            Optional<FriendRequest> findExist = friendRequestRepository.findById(friendRelationShipId);
//            if(!Objects.equals(findUser.getId(), findExist.get().getToUser().getId())) {
//                throw new Exception401("권한이 없습니다.");
//            }
//
//            if(findExist.isPresent()) {
//                FriendRequest findFriendRequest = findExist.get();
//                friendRequestRepository.delete(findFriendRequest);
//                return new FriendRequestResponseDTO.FriendRequestRejectDTO(findFriendRequest);
//            }
//            else {
//                throw new Exception404("해당 관계를 찾을 수 없습니다.");
//            }
//        } catch (Exception e) {
//            throw new Exception500("reject fail : " + e.getMessage());
//        }
//    }
//    @Override
//    @Transactional(readOnly = true)
//    public FriendRequestResponseDTO.FriendRequestFindAllDTO findAll(String userEmail) {
//        try {
//            User findUser = getUser(userEmail);
//            FriendRequestResponseDTO.FriendRequestFindAllDTO findFriendRequestList = friendRequestRepository.findAll(findUser.getId());
//            return findFriendRequestList;
//        } catch (Exception e) {
//            throw new Exception500("findAll fail : " + e.getMessage());
//        }
//    }
}
