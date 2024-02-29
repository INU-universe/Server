package universe.universe.common.auth.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import universe.universe.common.auth.PrincipalDetails;
import universe.universe.common.auth.jwt.JwtProperties;
import universe.universe.entitiy.token.RefreshToken;
import universe.universe.repository.token.TokenRepository;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {

    public static void generateAndSendToken(HttpServletResponse response, PrincipalDetails principalDetails, TokenRepository tokenRepository, String secretKey) throws IOException {
        // RSA 방식이 아닌, Hash 암호 방식이다.
        String accessToken = createToken("accessToken", JwtProperties.ACCESS_EXPIRATION_TIME, principalDetails, secretKey);

        String refreshToken = createToken("refreshToken", JwtProperties.REFRESH_EXPIRATION_TIME, principalDetails, secretKey);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
        tokenRepository.save(new RefreshToken(refreshToken));

        // 토큰을 JSON 형태로 만들어서 응답 본문에 추가
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("jwtToken", accessToken);
        response.setContentType("application/json");

        log.info("JWT Token : " + accessToken);
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

