package pl.kondziet.springbackend.infrastructure.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.infrastructure.security.userdetails.AppUserDetails;
import pl.kondziet.springbackend.domain.model.entity.User;
import pl.kondziet.springbackend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userService;
    private final List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Optional<OAuth2UserInfoExtractor> oAuth2UserInfoExtractorOptional = oAuth2UserInfoExtractors.stream()
                .filter(oAuth2UserInfoExtractor -> oAuth2UserInfoExtractor.supportsProvider(userRequest))
                .findFirst();

        if (oAuth2UserInfoExtractorOptional.isEmpty()) {
            throw new InternalAuthenticationServiceException("The OAuth2 provider is not supported yet");
        }

        AppUserDetails appUserDetails = oAuth2UserInfoExtractorOptional.get().extractUserInfo(oAuth2User);
        User user = persistUserDetails(appUserDetails);
        appUserDetails.setId(user.getId());

        return appUserDetails;
    }

    private User persistUserDetails(AppUserDetails userDetails) {
        Optional<User> existingUser = userService.findByUsername(userDetails.getUsername());

        if (existingUser.isPresent()) {
            return updateExistingUser(existingUser.get(), userDetails);
        }

        User newUser = createNewUser(userDetails);
        return userService.save(newUser);
    }

    private User updateExistingUser(User existingUser, AppUserDetails appUserDetails) {
        existingUser.setEmail(appUserDetails.getEmail());
        existingUser.setImageUrl(appUserDetails.getImageUrl());
        return userService.save(existingUser);
    }

    private User createNewUser(AppUserDetails appUserDetails) {
        User newUser = User.builder()
                .username(appUserDetails.getUsername())
                .email(appUserDetails.getEmail())
                .imageUrl(appUserDetails.getImageUrl())
                .provider(appUserDetails.getProvider())
                .build();
        return userService.save(newUser);
    }
}
