package pl.kondziet.springbackend.infrastructure.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.infrastructure.security.userdetails.AppUserDetails;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GenerateTokenService {

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    private final JwtFacade jwtFacade;

    public String generateAccessToken(UserDetails userDetails) {

        return jwtFacade.buildToken(
                userDetails,
                new HashMap<>(),
                accessTokenExpiration
        );
    }

    public String generateRefreshToken(UserDetails userDetails) {

        return jwtFacade.buildToken(
                userDetails,
                new HashMap<>(),
                refreshTokenExpiration
        );
    }
}
