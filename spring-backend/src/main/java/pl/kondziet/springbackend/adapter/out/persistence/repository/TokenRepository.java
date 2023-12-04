package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.TokenJpaEntity;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenJpaEntity, UUID> {
}
