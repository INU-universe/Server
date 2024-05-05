package universe.universe.domain.token.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.reponse.ErrorCode;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
    private Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();
    @Override
    public void logout(String authHeader) {
        try {
            log.info("[TokenServiceImpl] logout");
            Optional<String> optionalToken = extractToken(authHeader);
            if (optionalToken.isPresent()) {
                String token = optionalToken.get();
                blacklistToken(token);
                log.error("Logout 성공");
            } else {
                throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
            }
        } catch (CustomException ce){
            log.info("[CustomException] TokenServiceImpl logout");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] TokenServiceImpl logout");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] TokenServiceImpl logout : " + e.getMessage());
        }
    }
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public Optional<String> extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
