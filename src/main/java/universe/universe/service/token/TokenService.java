package universe.universe.service.token;

import universe.universe.dto.token.TokenRequestDTO;
import universe.universe.dto.token.TokenResponseDTO;
import universe.universe.entitiy.token.RefreshToken;

public interface TokenService {
    TokenResponseDTO.TokenGetAccessTokenDTO getAccessToken(TokenRequestDTO.TokenGetAccessTokenDTO tokenGetAccessTokenDTO);
}
