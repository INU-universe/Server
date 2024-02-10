package universe.universe.common.auth.jwt;

public interface JwtProperties {
    String SECRET = "cos";
    int ACCESS_EXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    long REFRESH_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일을 밀리초로 표현
}
