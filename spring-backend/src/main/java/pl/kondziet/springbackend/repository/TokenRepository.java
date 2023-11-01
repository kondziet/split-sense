package pl.kondziet.springbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.security.token.Token;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
}
