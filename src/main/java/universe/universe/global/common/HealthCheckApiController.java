package universe.universe.global.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import universe.universe.global.common.reponse.ApiResponse;

@RestController
@RequestMapping("/api/healthCheck")
@RequiredArgsConstructor
@Slf4j
public class HealthCheckApiController {
    @GetMapping
    public ResponseEntity<?> healthCheck() {
        String result = "202405222112 healthCheck 완료";
        return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] HealthCheckApiController healthCheck", result));
    }
}
