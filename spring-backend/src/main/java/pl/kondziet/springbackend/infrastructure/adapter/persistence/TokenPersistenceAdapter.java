package pl.kondziet.springbackend.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.TokenJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.repository.TokenRepository;
import pl.kondziet.springbackend.application.port.out.TokenPersistencePort;
import pl.kondziet.springbackend.infrastructure.mapper.TokenMapper;
import pl.kondziet.springbackend.infrastructure.security.token.Token;

@Component
@RequiredArgsConstructor
public class TokenPersistenceAdapter implements TokenPersistencePort {

    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    @Override
    public Token saveToken(Token token) {
        TokenJpaEntity savedToken = tokenRepository.save(
                tokenMapper.tokenToTokenJpaEntity(token)
        );

        return tokenMapper.tokenJpaEntityToToken(savedToken);
    }
}
