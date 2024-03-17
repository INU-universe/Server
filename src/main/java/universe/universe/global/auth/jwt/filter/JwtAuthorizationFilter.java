package universe.universe.global.auth.jwt.filter;

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
import universe.universe.global.auth.PrincipalDetails;
import universe.universe.global.auth.jwt.JwtProperties;
import universe.universe.global.common.exception.Exception404;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.domain.token.service.TokenServiceImpl;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    private TokenServiceImpl tokenService;
    @Value(("${jwt.secret}")) // application.properties 또는 yml 파일의 jwt.secret 값을 여기에 주입
    private String secretKey; // JWT 토큰 생성 및 파싱에 사용할 비밀키

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenServiceImpl tokenService, String secretKey) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.secretKey = secretKey;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        String userEmail = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(jwtToken).getClaim("userEmail").asString();

        // 블랙리스트에 토큰이 있는지 확인
        if (tokenService.isTokenBlacklisted(jwtToken)) {
            throw new Exception404("블랙리스트에 해당하는 토큰입니다.");
        }

        if (userEmail != null) {
            User Entity = userRepository.findByUserEmail(userEmail).get();

            PrincipalDetails principalDetails = new PrincipalDetails(Entity);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, principalDetails.getPassword(), principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
