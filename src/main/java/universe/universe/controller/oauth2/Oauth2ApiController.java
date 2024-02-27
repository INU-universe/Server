package universe.universe.controller.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import universe.universe.entitiy.user.User;
import universe.universe.repository.user.UserRepository;
import universe.universe.service.auth.AuthenticationService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Oauth2ApiController {
    final private UserRepository userRepository;
    final private AuthenticationService authenticationService;

//    @GetMapping("/login/oauth2/code/google")
//    public String Oauth2Login() {
////        String userEmail = getUserEmail();
//        return "index";
//
//    }

    @GetMapping("/login/oauth2/code/google")
    public @ResponseBody String loginOauthTest(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication = " + oAuth2User.getName());
        System.out.println("oauth = " + oauth.getName());
        return "Oauth 세션 정보 확인하기";
    }

    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
