package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.infrastructure.security.token.Token;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
}
