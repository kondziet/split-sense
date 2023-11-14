package pl.kondziet.springbackend.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.port.in.TokenUseCase;
import pl.kondziet.springbackend.application.port.out.SaveTokenPort;
import pl.kondziet.springbackend.infrastructure.security.token.JwtFacade;
import pl.kondziet.springbackend.infrastructure.security.token.Token;
import pl.kondziet.springbackend.infrastructure.security.token.TokenType;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenService implements TokenUseCase {

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    private final SaveTokenPort saveTokenPort;
    private final JwtFacade jwtFacade;

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        String builtToken = jwtFacade.buildToken(
                userDetails,
                Map.of("providerId", "splitsense.com"),
                accessTokenExpiration
        );

        Token accessToken = Token.builder()
                .type(TokenType.ACCESS_TOKEN)
                .expired(false)
                .revoked(false)
                .content(builtToken)
                .build();

        Token savedToken = saveTokenPort.saveToken(accessToken);

        return savedToken.getContent();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        String builtToken = jwtFacade.buildToken(
                userDetails,
                Map.of("providerId", "splitsense.com"),
                refreshTokenExpiration
        );

        Token refreshToken = Token.builder()
                .type(TokenType.REFRESH_TOKEN)
                .expired(false)
                .revoked(false)
                .content(builtToken)
                .build();

        Token savedToken = saveTokenPort.saveToken(refreshToken);

        return savedToken.getContent();
    }
}
