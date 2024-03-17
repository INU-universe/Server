package universe.universe.global.auth.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import universe.universe.global.auth.PrincipalDetails;
import universe.universe.global.auth.oauth2.provider.GoogleUserInfo;
import universe.universe.global.auth.oauth2.provider.Oauth2UserInfo;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        Oauth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }  else {
            log.info("구글 로그인만 제공한다.");
        }

        String userInfoEmail = oAuth2UserInfo.getEmail();
        String userInfoName = oAuth2UserInfo.getName();
        String userInfoImg = oAuth2UserInfo.getImg();

        log.info("userInfoEmail: {}", userInfoEmail);
        log.info("userInfoName: {}", userInfoName);
        log.info("userInfoImg: {}", userInfoImg);

        User user = userRepository.findByUserEmail(userInfoEmail).get();

        if (user != null) {
            log.info("로그인을 이미 했음, 자동 회원가입이 되어있다.");
        }  else {
            user = User.builder()
                    .userEmail(userInfoEmail)
                    .userName(userInfoName)
                    .userImg(userInfoImg)
                    .role("ROLE_USER")
//                    .userPassword(null)
//                    .provider(oAuth2UserInfo.getProvider())
//                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
