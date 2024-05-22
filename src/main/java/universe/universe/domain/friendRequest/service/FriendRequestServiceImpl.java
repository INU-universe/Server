package universe.universe.domain.friendRequest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import universe.universe.global.auth.jwt.util.JwtProperties;
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

            String friendRequestURL = token;

            FriendRequestResponseDTO.FriendRequestGetURLDTO result = new FriendRequestResponseDTO.FriendRequestGetURLDTO();
            result.setFriendRequestURL(friendRequestURL);
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] FriendRequestServiceImpl getURL");
            throw ce;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] FriendRequestServiceImpl getURL : " + e.getMessage());
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
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] FriendRequestServiceImpl acceptURL : " + e.getMessage());
        }
    }
}
