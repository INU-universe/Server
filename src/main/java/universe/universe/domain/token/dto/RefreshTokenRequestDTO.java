package universe.universe.domain.token.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RefreshTokenRequestDTO {
    @Getter
    @Setter
    public static class RefreshTokenGetAccessTokenDTO {
        private String refreshToken;
    }
}
