package universe.universe.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;

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
            Optional<String> optionalToken = extractToken(authHeader);
            if (optionalToken.isPresent()) {
                String token = optionalToken.get();
                blacklistToken(token);
                log.error("Logout 성공");
                for(int i=0; i< blacklistedTokens.size(); i++) {
                    System.out.println("blackList: " + blacklistedTokens);
                }
            } else {
                throw new Exception404("해당 토큰을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            throw new Exception500("logout fail : " + e.getMessage());
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
