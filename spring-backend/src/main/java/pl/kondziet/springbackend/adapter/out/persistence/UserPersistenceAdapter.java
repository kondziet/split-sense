package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.repository.UserRepository;
import pl.kondziet.springbackend.application.port.out.LoadUserDetailsPort;
import pl.kondziet.springbackend.infrastructure.mapper.UserMapper;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserDetailsPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserDetails loadUserDetails(String email) {
        return userMapper.userJpaEntityToUser(userRepository.findByEmail(email).orElseThrow());
    }
}
