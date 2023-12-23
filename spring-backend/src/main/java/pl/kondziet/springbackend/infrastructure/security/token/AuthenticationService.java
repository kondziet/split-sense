package pl.kondziet.springbackend.infrastructure.security.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.service.dto.SignInRequest;
import pl.kondziet.springbackend.application.service.dto.SignInResponse;
import pl.kondziet.springbackend.application.service.dto.TokenRefreshResponse;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final GenerateTokenService generateTokenService;
    private final JwtFacade jwtFacade;

    public SignInResponse authenticate(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.username(),
                        signInRequest.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String generatedAccessToken = generateTokenService.generateAccessToken(userDetails);
        String generatedRefreshToken = generateTokenService.generateRefreshToken(userDetails);

        return SignInResponse.builder()
                .accessToken(generatedAccessToken)
                .refreshToken(generatedRefreshToken)
                .build();
    }

    public TokenRefreshResponse refreshAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization Header");
        }
        String refreshToken = authorizationHeader.substring(7);
        String username = jwtFacade.extractUsername(refreshToken);

        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtFacade.isTokenValid(refreshToken, userDetails)) {
                String accessToken = generateTokenService.generateAccessToken(userDetails);
                return TokenRefreshResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }

        throw new IllegalArgumentException("Invalid username in refresh token");
    }
}
