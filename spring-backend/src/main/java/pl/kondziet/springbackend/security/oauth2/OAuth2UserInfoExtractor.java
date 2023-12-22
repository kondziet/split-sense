package pl.kondziet.springbackend.security.oauth2;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pl.kondziet.springbackend.security.userdetails.AppUserDetails;

public interface OAuth2UserInfoExtractor {

    AppUserDetails extractUserInfo(OAuth2User oAuth2User);

    boolean supportsProvider(OAuth2UserRequest userRequest);
}
