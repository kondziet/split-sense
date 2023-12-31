package pl.kondziet.springbackend.infrastructure.security.userdetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pl.kondziet.springbackend.infrastructure.security.oauth2.OAuth2Provider;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserDetails implements OAuth2User, UserDetails {

    private UUID id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String imageUrl;
    private OAuth2Provider provider;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
