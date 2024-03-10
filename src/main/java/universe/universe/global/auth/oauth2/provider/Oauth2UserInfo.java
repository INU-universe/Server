package universe.universe.global.auth.oauth2.provider;

public interface Oauth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
    String getImg();
}
