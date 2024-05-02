package universe.universe.domain.token.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.auth.jwt.provider.JwtProvider;
import universe.universe.global.common.exception.CustomException;
import universe.universe.domain.token.dto.RefreshTokenRequestDTO;
import universe.universe.domain.token.dto.RefreshTokenResponseDTO;
import universe.universe.domain.token.entity.RefreshToken;
import universe.universe.domain.user.entity.User;
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.CommonMethod;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtProvider jwtProvider;
    private final CommonMethod commonMethod;
    @Override
    public RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO getAccessToken(RefreshTokenRequestDTO.RefreshTokenGetAccessTokenDTO tokenGetAccessTokenDTO) {
        try {
            log.info("[RefreshTokenServiceImpl] getAccessToken");
            RefreshToken findRefreshToken = commonMethod.getRefreshToken(tokenGetAccessTokenDTO.getRefreshToken());
            User findUser = commonMethod.getUser("id", findRefreshToken.getUserId());

            String accessToken = jwtProvider.generateToken(findUser);

            return new RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO(accessToken, findRefreshToken.getRefreshToken());
        } catch (CustomException ce){
            log.info("[CustomException] RefreshTokenServiceImpl getAccessToken");
            throw ce;
        } catch (TokenExpiredException te) {
            log.info("[TokenExpiredException] RefreshTokenServiceImpl getAccessToken");
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            log.info("[TokenExpiredException] RefreshTokenServiceImpl getAccessToken");
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
