package universe.universe.controller.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import universe.universe.repository.user.UserRepository;

@RestController
@RequestMapping("api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class Oauth2ApiController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public @ResponseBody String Oauth2Login(Authentication authentication) {
        return "Oauth 세션 정보 확인하기";
    }
}
