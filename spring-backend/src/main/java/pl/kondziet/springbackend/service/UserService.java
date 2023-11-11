package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;

public interface UserService {

    UserJpaEntity save(UserJpaEntity userJpaEntity);
    UserJpaEntity findByEmail(String email);
    void updateEmail(UserJpaEntity userJpaEntity, String email);
}
