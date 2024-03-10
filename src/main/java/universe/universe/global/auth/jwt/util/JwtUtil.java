package universe.universe.global.auth.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import universe.universe.global.auth.PrincipalDetails;
import universe.universe.global.auth.jwt.JwtProperties;
import universe.universe.domain.token.entity.RefreshToken;
import universe.universe.domain.token.repository.RefreshTokenRepository;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {

    public static void generateAndSendToken(HttpServletResponse response, PrincipalDetails principalDetails, RefreshTokenRepository tokenRepository, String secretKey) throws IOException {
        String accessToken = createToken("accessToken", JwtProperties.ACCESS_EXPIRATION_TIME, principalDetails, secretKey);
        String refreshToken = createToken("refreshToken", JwtProperties.REFRESH_EXPIRATION_TIME, principalDetails, secretKey);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
        tokenRepository.save(new RefreshToken(refreshToken, principalDetails.getUser().getId()));

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        response.setContentType("application/json");

        log.info("Access Token : " + accessToken);
        log.info("Refresh Token : " + refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
    }

    private static String createToken(String type, long expirationTime, PrincipalDetails principalDetails, String secretKey) {
        String token = JWT.create()
                .withSubject(type)  // subject 변경
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간 변경
                .withClaim("userEmail", principalDetails.getUser().getUserEmail())
                .withClaim("role", principalDetails.getUser().getRole())
                .sign(Algorithm.HMAC512(secretKey));
        return token;
    }
}

