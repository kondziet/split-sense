package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import pl.kondziet.springbackend.adapter.out.persistence.entity.TokenJpaEntity;
import pl.kondziet.springbackend.infrastructure.security.token.Token;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenJpaEntity tokenToTokenJpaEntity(Token token);
    Token tokenJpaEntityToToken(TokenJpaEntity tokenJpaEntity);
}
