package pl.kondziet.springbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.UserRepository;
import pl.kondziet.springbackend.service.UserService;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserJpaEntity save(UserJpaEntity userJpaEntity) {
        return userRepository.save(userJpaEntity);
    }

    @Cacheable(value = "users", key = "#email")
    @Override
    public UserJpaEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @CacheEvict(value = "users", key = "#email")
    @Override
    public void updateEmail(UserJpaEntity userJpaEntity, String email) {
        userJpaEntity.setEmail(email);
    }
}
