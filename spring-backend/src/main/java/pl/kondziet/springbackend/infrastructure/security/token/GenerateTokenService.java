package pl.kondziet.springbackend.infrastructure.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.port.out.TokenOutputPort;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class GenerateTokenService {

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    private final TokenOutputPort tokenOutputPort;
    private final JwtFacade jwtFacade;

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

        Token savedToken = tokenOutputPort.saveToken(accessToken);

        return savedToken.getContent();
    }

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

        Token savedToken = tokenOutputPort.saveToken(refreshToken);

        return savedToken.getContent();
    }
}
