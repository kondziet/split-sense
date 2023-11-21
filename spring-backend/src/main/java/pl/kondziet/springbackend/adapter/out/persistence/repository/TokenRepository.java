package pl.kondziet.springbackend.adapter.out.persistence.repository;

import pl.kondziet.springbackend.infrastructure.security.token.Token;

public interface TokenRepository {

    Token save(Token token);
}
