package universe.universe.domain.token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.auth.jwt.provider.JwtProvider;
import universe.universe.global.exception.Exception404;
import universe.universe.global.exception.Exception500;
import universe.universe.domain.token.dto.RefreshTokenRequestDTO;
import universe.universe.domain.token.dto.RefreshTokenResponseDTO;
import universe.universe.domain.token.entity.RefreshToken;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.token.repository.RefreshTokenRepository;
import universe.universe.domain.user.repository.UserRepository;

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
            RefreshToken findRefreshToken = getRefreshToken(tokenGetAccessTokenDTO.getRefreshToken());
            User findUser = getUser_Id(findRefreshToken.getUserId());

            String accessToken = jwtProvider.generateToken(findUser);

            RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO result = new RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO(accessToken, findRefreshToken.getRefreshToken());

            return result;
        } catch (Exception e) {
            throw new Exception500("refreshToken getAccessToken fail : " + e.getMessage());
        }
    }
    private User getUser_Id(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    private RefreshToken getRefreshToken(String refreshToken) {
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findById(refreshToken);
        if(!findRefreshToken.isPresent()) {
            throw new Exception404("해당 refresh token을 찾을 수 없습니다.");
        }
        return findRefreshToken.get();
    }
}
