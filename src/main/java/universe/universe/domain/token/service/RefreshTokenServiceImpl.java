package universe.universe.domain.token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.auth.jwt.provider.JwtProvider;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.exception.Exception500;
import universe.universe.domain.token.dto.RefreshTokenRequestDTO;
import universe.universe.domain.token.dto.RefreshTokenResponseDTO;
import universe.universe.domain.token.entity.RefreshToken;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.token.repository.RefreshTokenRepository;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.reponse.ErrorCode;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    final private RefreshTokenRepository refreshTokenRepository;
    final private UserRepository userRepository;
    final private JwtProvider jwtProvider;
    @Override
    public RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO getAccessToken(RefreshTokenRequestDTO.RefreshTokenGetAccessTokenDTO tokenGetAccessTokenDTO) {
        try {
            log.info("[RefreshTokenServiceImpl] getAccessToken");
            RefreshToken findRefreshToken = getRefreshToken(tokenGetAccessTokenDTO.getRefreshToken());
            User findUser = getUser("id", findRefreshToken.getUserId());

            String accessToken = jwtProvider.generateToken(findUser);

            RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO result = new RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO(accessToken, findRefreshToken.getRefreshToken());

            return result;
        } catch (CustomException ce){
            log.info("[CustomException] RefreshTokenServiceImpl getAccessToken");
            throw ce;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.SERVER_ERROR, "RefreshTokenServiceImpl getAccessToken : " + e.getMessage());
        }
    }
    private User getUser(String type, Object value) throws CustomException {
        Optional<User> findUser = null;
        if (type.equals("email")) {
            findUser = userRepository.findByUserEmail((String) value);
        } else if (type.equals("id")) {
            findUser = userRepository.findById((Long) value);
        }

        if (findUser == null || !findUser.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return findUser.get();
    }

    private RefreshToken getRefreshToken(String refreshToken) throws CustomException {
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findById(refreshToken);
        if(!findRefreshToken.isPresent()) {
            throw new CustomException(ErrorCode.REFRESH_NOT_FOUND);
        }
        return findRefreshToken.get();
    }
}
