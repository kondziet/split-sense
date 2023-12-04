package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.TokenJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.TokenTypeJpaEntity;
import pl.kondziet.springbackend.infrastructure.security.token.Token;
import pl.kondziet.springbackend.infrastructure.security.token.TokenType;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    @Mapping(target = "type", source = "token.type", qualifiedByName = "tokenTypeToTokenTypeJpaEntity")
    TokenJpaEntity tokenToTokenJpaEntity(Token token);

    @Mapping(target = "type", source = "tokenJpaEntity.type", qualifiedByName = "tokenTypeJpaEntityToTokenType")
    Token tokenJpaEntityToToken(TokenJpaEntity tokenJpaEntity);

    @Named("tokenTypeToTokenTypeJpaEntity")
    default TokenTypeJpaEntity tokenTypeToTokenTypeJpaEntity(TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS_TOKEN -> TokenTypeJpaEntity.ACCESS_TOKEN;
            case REFRESH_TOKEN -> TokenTypeJpaEntity.REFRESH_TOKEN;
        };
    }

    @Named("tokenTypeJpaEntityToTokenType")
    default TokenType tokenTypeJpaEntityToTokenType(TokenTypeJpaEntity tokenTypeJpaEntity) {
        return switch (tokenTypeJpaEntity) {
            case ACCESS_TOKEN -> TokenType.ACCESS_TOKEN;
            case REFRESH_TOKEN -> TokenType.REFRESH_TOKEN;
        };
    }

}
