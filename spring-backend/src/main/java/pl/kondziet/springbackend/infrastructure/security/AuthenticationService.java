package pl.kondziet.springbackend.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.dto.SignInRequest;
import pl.kondziet.springbackend.domain.dto.SignInResponse;
import pl.kondziet.springbackend.domain.dto.TokenRefreshResponse;
import pl.kondziet.springbackend.application.port.in.AuthenticationUseCase;
import pl.kondziet.springbackend.application.port.in.RefreshAuthenticationUseCase;
import pl.kondziet.springbackend.infrastructure.security.token.GenerateTokenService;
import pl.kondziet.springbackend.infrastructure.security.token.JwtFacade;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements AuthenticationUseCase, RefreshAuthenticationUseCase {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final GenerateTokenService generateTokenService;
    private final JwtFacade jwtFacade;

    @Override
    public SignInResponse authenticate(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.email());
        String generatedAccessToken = generateTokenService.generateAccessToken(userDetails);
        String generatedRefreshToken = generateTokenService.generateRefreshToken(userDetails);

        return SignInResponse.builder()
                .accessToken(generatedAccessToken)
                .refreshToken(generatedRefreshToken)
                .build();
    }

    @Override
    public TokenRefreshResponse refreshAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization Header");
        }
        String refreshToken = authorizationHeader.substring(7);
        String userEmail = jwtFacade.extractUserEmail(refreshToken);

        if (userEmail != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtFacade.isTokenValid(refreshToken, userDetails)) {
                String accessToken = generateTokenService.generateAccessToken(userDetails);
                return TokenRefreshResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }

        throw new IllegalArgumentException("Invalid Email in Refresh Token");
    }
}
