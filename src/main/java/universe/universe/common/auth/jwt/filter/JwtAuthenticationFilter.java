package universe.universe.common.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import universe.universe.common.auth.PrincipalDetails;
import universe.universe.common.auth.jwt.util.JwtUtil;
import universe.universe.entitiy.user.User;
import universe.universe.repository.token.RefreshTokenRepository;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository tokenRepository;
    private final String secretKey;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
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
//        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
        log.info("Login 성공!");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        JwtUtil.generateAndSendToken(response, principalDetails, tokenRepository, secretKey);
    }
}
