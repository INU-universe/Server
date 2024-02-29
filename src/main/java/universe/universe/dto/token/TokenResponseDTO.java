package universe.universe.dto.token;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TokenResponseDTO {
    @Setter
    @Getter
    public static class TokenGetAccessTokenDTO {
        private String accessToken;
        private String refreshToken;

        public TokenGetAccessTokenDTO(String accessToken, String findRefreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = findRefreshToken;
        }
    }
}
