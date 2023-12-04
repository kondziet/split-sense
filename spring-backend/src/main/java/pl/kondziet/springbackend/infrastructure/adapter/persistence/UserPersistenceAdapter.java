package pl.kondziet.springbackend.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.repository.UserRepository;
import pl.kondziet.springbackend.domain.model.entity.User;
import pl.kondziet.springbackend.application.port.out.UserPersistencePort;
import pl.kondziet.springbackend.infrastructure.mapper.UserMapper;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User loadUserByEmail(String email) {
        UserJpaEntity userJpaEntity = userRepository.findByEmail(email).orElseThrow();

        return userMapper.userJpaEntityToUser(userJpaEntity);
    }
}
