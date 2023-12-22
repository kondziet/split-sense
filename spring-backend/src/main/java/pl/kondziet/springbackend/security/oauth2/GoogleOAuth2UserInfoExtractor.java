package pl.kondziet.springbackend.security.oauth2;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.security.userdetails.AppUserDetails;

import java.util.Collections;

@Service
public class GoogleOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

    @Override
    public AppUserDetails extractUserInfo(OAuth2User oAuth2User) {

        return AppUserDetails.builder()
                .username(retrieveAttribute("email", oAuth2User))
                .name(retrieveAttribute("name", oAuth2User))
                .email(retrieveAttribute("email", oAuth2User))
                .imageUrl(retrieveAttribute("picture", oAuth2User))
                .provider(OAuth2Provider.GOOGLE)
                .attributes(oAuth2User.getAttributes())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("USER")))
                .build();
    }

    @Override
    public boolean supportsProvider(OAuth2UserRequest userRequest) {
        return OAuth2Provider.GOOGLE.name().equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
    }

    private String retrieveAttribute(String attribute, OAuth2User oAuth2User) {
        Object retrieved = oAuth2User.getAttributes().get(attribute);
        return retrieved == null ? "" : retrieved.toString();
    }
}
