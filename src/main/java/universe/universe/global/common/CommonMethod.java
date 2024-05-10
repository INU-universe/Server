package universe.universe.global.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.domain.friend.repository.FriendRepository;
import universe.universe.domain.location.entity.Location;
import universe.universe.domain.token.entity.RefreshToken;
import universe.universe.domain.token.repository.RefreshTokenRepository;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.reponse.ErrorCode;

import java.util.Optional;

import static universe.universe.global.common.reponse.ErrorCode.MESSAGE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommonMethod {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    public final FriendRepository friendRepository;

    /** User Method **/
    public User getUser(String identifier, Object value) throws CustomException {
        Optional<User> findUser = null;
        if (identifier.equals("email")) {
            findUser = userRepository.findByUserEmail((String) value);
        } else if (identifier.equals("id")) {
            findUser = userRepository.findById((Long) value);
        }

        if (findUser == null || !findUser.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return findUser.get();
    }

    /** Refresh Method **/
    public RefreshToken getRefreshToken(String refreshToken) throws CustomException {
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findById(refreshToken);
        if(!findRefreshToken.isPresent()) {
            throw new CustomException(ErrorCode.REFRESH_NOT_FOUND);
        }
        return findRefreshToken.get();
    }


    /** Location Method **/
    public Location getLocation_Email(String userEmail) throws CustomException  {
        Location findLocation = getUser("email", userEmail).getLocation();
        if(findLocation == null) {
            throw new CustomException(ErrorCode.LOCATION_NOT_FOUND);
        }
        return findLocation;
    }

}
