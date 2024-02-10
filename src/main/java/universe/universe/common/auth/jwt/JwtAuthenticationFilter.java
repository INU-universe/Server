package universe.universe.common.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import universe.universe.common.auth.PrincipalDetails;
import universe.universe.entitiy.token.RefreshToken;
import universe.universe.entitiy.user.User;
import universe.universe.repository.token.TokenRepository;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 1. username, password 를 받아서
        // 2. 정상인지 로그인 시도를 해본다. authenticationManager로 로그인 시도를 하면,
        // PrincipalDetailsService가 호출된다.
        // 그 후 loadUserByUsername이 실행이 된다.

        // 3. PrincipalDetails를 세션에 담고 (권한 관리를 위해서)
        // 4. JWT 토큰을 만들어서 응답하면 됨.
        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword());

            // PrincipalDetailsService 의 loadUserByUsername() 함수가 실행된다.
            // 그 후 정상이면 authentication이 리턴된다.
            // DB에 있는 username과 password가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken); // 내 로그인 한 정보가 담긴다.

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 여기서 오류가 터진다.

            // authentication 객체가 session 영역에 저장을 해야 하고 그 방법이 return 해주면 된다.
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는 거다.
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없다. 근데 단지 권한 처리 때문에 session에 넣어준다.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행된다.
    // JWT 토큰을 만들면서 request 요청한 사용자에게 JWT 토큰을 response 해주면 된다.
    // 비밀번호를 잘못 입력하면 401 에러가 뜨며 해당 메서드가 실행이 안된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA 방식이 아닌, Hash 암호 방식이다.
        String accessToken = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료 시간 10분
                .withClaim("userEmail", principalDetails.getUser().getUserEmail())
                .withClaim("role", principalDetails.getUser().getRole())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 고유한 값

        String refreshToken = JWT.create()
                .withSubject("refreshToken")  // subject 변경
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME)) // 만료 시간 변경
                .withClaim("userEmail", principalDetails.getUser().getUserEmail())
                .withClaim("role", principalDetails.getUser().getRole())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
        tokenRepository.save(new RefreshToken(refreshToken));

        // 토큰을 JSON 형태로 만들어서 응답 본문에 추가
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("jwtToken", accessToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
    }
}
