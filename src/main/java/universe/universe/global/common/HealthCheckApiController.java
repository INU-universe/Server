package universe.universe.global.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healthCheck")
@RequiredArgsConstructor
@Slf4j
public class HealthCheckApiController {
    // 자동 배포 확인
    @GetMapping
    public String init() {
        return "init: CI/CD Success7";
    }
}
