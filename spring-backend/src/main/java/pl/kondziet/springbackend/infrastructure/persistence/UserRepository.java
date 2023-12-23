package pl.kondziet.springbackend.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.domain.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
