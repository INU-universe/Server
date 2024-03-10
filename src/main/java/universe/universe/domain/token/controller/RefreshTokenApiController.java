package universe.universe.domain.token.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import universe.universe.global.exception.Exception400;
import universe.universe.global.exception.Exception500;
import universe.universe.global.reponse.ApiResponse;
import universe.universe.domain.token.dto.RefreshTokenRequestDTO;
import universe.universe.domain.token.dto.RefreshTokenResponseDTO;
import universe.universe.domain.token.service.RefreshTokenServiceImpl;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenApiController {
    private final RefreshTokenServiceImpl refreshTokenService;

    @PostMapping("/getAccessToken")
    public ResponseEntity<?> getAccessToken(@RequestBody RefreshTokenRequestDTO.RefreshTokenGetAccessTokenDTO tokenGetAccessTokenDTO) {
        try {
            RefreshTokenResponseDTO.RefreshTokenGetAccessTokenDTO getAccessToken = refreshTokenService.getAccessToken(tokenGetAccessTokenDTO);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "RefreshToken 발급이 완료되었습니다.", getAccessToken));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
}
