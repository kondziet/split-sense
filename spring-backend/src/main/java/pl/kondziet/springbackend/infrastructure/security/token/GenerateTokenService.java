package pl.kondziet.springbackend.infrastructure.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.infrastructure.security.userdetails.AppUserDetails;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class GenerateTokenService {

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    private final JwtFacade jwtFacade;

    public String generateAccessToken(AppUserDetails appUserDetails) {

        return jwtFacade.buildToken(
                appUserDetails,
                Map.of("providerId", "splitsense.com"),
                accessTokenExpiration
        );
    }

    public String generateRefreshToken(AppUserDetails appUserDetails) {

        return jwtFacade.buildToken(
                appUserDetails,
                Map.of("providerId", "splitsense.com"),
                refreshTokenExpiration
        );
    }
}
