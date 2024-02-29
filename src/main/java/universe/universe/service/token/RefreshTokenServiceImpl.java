package universe.universe.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.auth.jwt.provider.JwtProvider;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.token.RefreshTokenRequestDTO;
import universe.universe.dto.token.RefreshTokenResponseDTO;
import universe.universe.entitiy.token.RefreshToken;
import universe.universe.entitiy.user.User;
import universe.universe.repository.token.RefreshTokenRepository;
import universe.universe.repository.user.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    final private RefreshTokenRepository tokenRepository;
    final private UserRepository userRepository;
    final private JwtProvider jwtProvider;
    @Override
    public RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO getAccessToken(RefreshTokenRequestDTO.RefreshTokenGetAccessTokenDTO tokenGetAccessTokenDTO) {
        try {
            RefreshToken findRefreshToken = getRefreshToken(tokenGetAccessTokenDTO.getRefreshToken());
            User findUser = getUserId(findRefreshToken.getUserId());

            String accessToken = jwtProvider.generateToken(findUser);

            RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO tokenGetAccessToken = new RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO(accessToken, findRefreshToken.getRefreshToken());

            return tokenGetAccessToken;
        } catch (Exception e) {
            throw new Exception500("getAccessToken fail : " + e.getMessage());
        }
    }
    private User getUserId(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    private RefreshToken getRefreshToken(String refreshToken) {
        Optional<RefreshToken> findRefreshToken = tokenRepository.findById(refreshToken);
        if(!findRefreshToken.isPresent()) {
            throw new Exception404("해당 refresh token을 찾을 수 없습니다.");
        }
        return findRefreshToken.get();
    }
}
