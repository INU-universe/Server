package universe.universe.common.auth.jwt.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value(("${jwt.secret}"))
    private String secretKey;

    public String generateToken(String userEmail, String role) {
        // 토큰 만료 시간 설정
        long accessTokenExpirationTime = 10 * 60 * 1000; // 10분
        long refreshTokenExpirationTime = 24 * 60 * 60 * 1000; // 24시간

        String accessToken = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .withClaim("userEmail", userEmail)
                .withClaim("role", role)
                .sign(Algorithm.HMAC512(secretKey));

        String refreshToken = JWT.create()
                .withSubject("refreshToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .withClaim("userEmail", userEmail)
                .withClaim("role", role)
                .sign(Algorithm.HMAC512(secretKey));

        // AccessToken과 RefreshToken을 반환
        return accessToken + ";" + refreshToken;
    }
}
