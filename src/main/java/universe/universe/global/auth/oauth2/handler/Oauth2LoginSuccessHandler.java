package universe.universe.global.auth.oauth2.handler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import universe.universe.global.auth.PrincipalDetails;
import universe.universe.global.auth.jwt.util.JwtUtil;
import universe.universe.domain.token.repository.RefreshTokenRepository;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Value(("${jwt.secret}"))
    private String secretKey;
    private final RefreshTokenRepository tokenRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//
//        // RSA 방식이 아닌, Hash 암호 방식이다.
//        String accessToken = JWT.create()
//                .withSubject("accessToken")
//                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
//                .withClaim("userEmail", principalDetails.getUser().getUserEmail())
//                .withClaim("role", principalDetails.getUser().getRole())
//                .sign(Algorithm.HMAC512(secretKey)); // 고유한 값
//
//        String refreshToken = JWT.create()
//                .withSubject("refreshToken")  // subject 변경
//                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME)) // 만료 시간 변경
//                .withClaim("userEmail", principalDetails.getUser().getUserEmail())
//                .withClaim("role", principalDetails.getUser().getRole())
//                .sign(Algorithm.HMAC512(secretKey));
//
//        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
//        tokenRepository.save(new RefreshToken(refreshToken));
//
//        // 토큰을 JSON 형태로 만들어서 응답 본문에 추가
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("jwtToken", accessToken);
//        response.setContentType("application/json");
//
//        log.info("JWT Token : " + accessToken);
//        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
        log.info("OAuth2 Login 성공!");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        JwtUtil.generateAndSendToken(response, principalDetails, tokenRepository, secretKey);
    }
}
