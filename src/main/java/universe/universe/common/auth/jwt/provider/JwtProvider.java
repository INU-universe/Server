package universe.universe.common.auth.jwt.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import universe.universe.entitiy.user.User;

import java.util.Date;

@Component
public class JwtProvider {
    @Value(("${jwt.secret}"))
    private String secretKey;

    public String generateToken(User user) {
        long accessTokenExpirationTime = 10 * 60 * 1000;

        String accessToken = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .withClaim("userEmail", user.getUserEmail())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(secretKey));

        return accessToken;
    }
}
