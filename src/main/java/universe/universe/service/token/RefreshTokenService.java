package universe.universe.service.token;

import universe.universe.dto.token.RefreshTokenRequestDTO;
import universe.universe.dto.token.RefreshTokenResponseDTO;

public interface RefreshTokenService {
    RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO getAccessToken(RefreshTokenRequestDTO.RefreshTokenGetAccessTokenDTO tokenGetAccessTokenDTO);
}
