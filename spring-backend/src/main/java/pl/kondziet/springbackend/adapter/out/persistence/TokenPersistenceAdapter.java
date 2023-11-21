package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.entity.TokenJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.TokenRepository;
import pl.kondziet.springbackend.application.port.out.TokenOutputPort;
import pl.kondziet.springbackend.infrastructure.mapper.TokenMapper;
import pl.kondziet.springbackend.infrastructure.security.token.Token;

@Component
@RequiredArgsConstructor
public class TokenPersistenceAdapter implements TokenOutputPort {

    private final TokenRepository tokenRepository;

    @Override
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }
}
