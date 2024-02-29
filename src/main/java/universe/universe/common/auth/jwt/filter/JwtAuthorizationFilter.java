package universe.universe.common.auth.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import universe.universe.common.auth.PrincipalDetails;
import universe.universe.common.auth.jwt.JwtProperties;
import universe.universe.entitiy.user.User;
import universe.universe.repository.user.UserRepository;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    @Value(("${jwt.secret}")) // application.properties 또는 yml 파일의 jwt.secret 값을 여기에 주입
    private String secretKey; // JWT 토큰 생성 및 파싱에 사용할 비밀키
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, String secretKey) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.secretKey = secretKey;
    }

    // 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게 된다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("jwtHeader = " + jwtHeader);
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        String userEmail = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(jwtToken).getClaim("userEmail").asString();

        // 서명이 정상적으로 됨
        if (userEmail != null) {
            User Entity = userRepository.findByUserEmail(userEmail);

            PrincipalDetails principalDetails = new PrincipalDetails(Entity);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, principalDetails.getPassword(), principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
