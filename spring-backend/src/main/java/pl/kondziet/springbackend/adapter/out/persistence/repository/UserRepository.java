package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.application.domain.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    UserJpaEntity save(UserJpaEntity userJpaEntity);
}
