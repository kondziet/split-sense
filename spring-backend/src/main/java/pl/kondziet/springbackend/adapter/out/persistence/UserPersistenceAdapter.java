package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.repository.UserRepository;
import pl.kondziet.springbackend.application.port.out.UserInputPort;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserInputPort {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserDetails(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
