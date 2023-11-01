package pl.kondziet.springbackend.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.repository.TokenRepository;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenService {

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public String generateAccessToken(UserDetails userDetails) {
        String builtToken = jwtService.buildToken(
                userDetails,
                Map.of("providerId", "splitsense.com"),
                accessTokenExpiration
        );

        Token accessToken = Token.builder()
                .tokenType(TokenType.ACCESS_TOKEN)
                .expired(false)
                .revoked(false)
                .content(builtToken)
                .build();

        Token savedToken = tokenRepository.save(accessToken);

        return savedToken.getContent();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String builtToken = jwtService.buildToken(
                userDetails,
                Map.of("providerId", "splitsense.com"),
                refreshTokenExpiration
        );

        Token refreshToken = Token.builder()
                .tokenType(TokenType.REFRESH_TOKEN)
                .expired(false)
                .revoked(false)
                .content(builtToken)
                .build();

        Token savedToken = tokenRepository.save(refreshToken);

        return savedToken.getContent();
    }

}
