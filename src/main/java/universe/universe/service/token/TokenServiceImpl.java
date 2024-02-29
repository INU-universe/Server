package universe.universe.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.auth.jwt.provider.JwtProvider;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.token.TokenRequestDTO;
import universe.universe.dto.token.TokenResponseDTO;
import universe.universe.entitiy.token.RefreshToken;
import universe.universe.entitiy.user.User;
import universe.universe.repository.token.TokenRepository;
import universe.universe.repository.user.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService{
    final private TokenRepository tokenRepository;
    final private UserRepository userRepository;
    final private JwtProvider jwtProvider;
    @Override
    public TokenResponseDTO.TokenGetAccessTokenDTO getAccessToken(TokenRequestDTO.TokenGetAccessTokenDTO tokenGetAccessTokenDTO) {
        try {
            RefreshToken findRefreshToken = getRefreshToken(tokenGetAccessTokenDTO.getRefreshToken());
            User findUser = getUserId(findRefreshToken.getUserId());

            String accessToken = jwtProvider.generateToken(findUser);

            TokenResponseDTO.TokenGetAccessTokenDTO tokenGetAccessToken = new TokenResponseDTO.TokenGetAccessTokenDTO(accessToken, findRefreshToken.getRefreshToken());

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
