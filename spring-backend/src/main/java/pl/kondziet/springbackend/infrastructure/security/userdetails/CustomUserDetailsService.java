package pl.kondziet.springbackend.infrastructure.security.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.model.entity.User;
import pl.kondziet.springbackend.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Username %s not found", username)));
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));

        return AppUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(authorities)
                .build();
    }
}
