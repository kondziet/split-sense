package pl.kondziet.springbackend.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.port.out.LoadUserDetailsPort;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoadUserDetailsPort loadUserDetailsPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserDetailsPort.loadUserDetails(username);
    }
}
