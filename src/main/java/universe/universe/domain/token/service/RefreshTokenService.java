package universe.universe.domain.token.service;

import universe.universe.domain.token.dto.RefreshTokenRequestDTO;
import universe.universe.domain.token.dto.RefreshTokenResponseDTO;

public interface RefreshTokenService {
    RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO getAccessToken(RefreshTokenRequestDTO.RefreshTokenGetAccessTokenDTO tokenGetAccessTokenDTO);
}
