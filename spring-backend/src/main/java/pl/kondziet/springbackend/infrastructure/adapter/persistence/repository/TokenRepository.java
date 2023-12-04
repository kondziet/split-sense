package pl.kondziet.springbackend.infrastructure.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.TokenJpaEntity;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenJpaEntity, UUID> {
}
