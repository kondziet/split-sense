package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;

public interface UserService {

    User save(User user);
    User findByEmail(String email);
    void updateEmail(User user, String email);
}
