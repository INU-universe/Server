package universe.universe.dto.token;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.token.RefreshToken;

@Data
public class TokenRequestDTO {
    @Getter
    @Setter
    public static class TokenGetAccessTokenDTO {
        private String refreshToken;
    }
}
