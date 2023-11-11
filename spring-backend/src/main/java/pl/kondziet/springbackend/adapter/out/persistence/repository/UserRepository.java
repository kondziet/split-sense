package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByEmail(String email);
    List<UserJpaEntity> findAllByIdIn(List<UUID> userIds);
}
