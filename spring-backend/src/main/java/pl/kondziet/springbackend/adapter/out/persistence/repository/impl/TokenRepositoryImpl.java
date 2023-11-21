package pl.kondziet.springbackend.adapter.out.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.TokenJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.TokenRepository;
import pl.kondziet.springbackend.infrastructure.mapper.TokenMapper;
import pl.kondziet.springbackend.infrastructure.security.token.Token;

@AllArgsConstructor
@Repository
@Transactional
public class TokenRepositoryImpl implements TokenRepository {

    @PersistenceContext
    private EntityManager em;
    private final TokenMapper tokenMapper;

    @Override
    public Token save(Token token) {
        TokenJpaEntity tokenJpaEntity = tokenMapper.tokenToTokenJpaEntity(token);
        em.persist(tokenJpaEntity);

        return tokenMapper.tokenJpaEntityToToken(tokenJpaEntity);
    }
}
