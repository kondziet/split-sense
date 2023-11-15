package pl.kondziet.springbackend.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.port.in.AuthenticationUseCase;
import pl.kondziet.springbackend.application.port.in.GenerateTokenUseCase;
import pl.kondziet.springbackend.application.port.in.command.AuthenticateCommand;
import pl.kondziet.springbackend.application.port.in.command.AuthenticationOutcome;
import pl.kondziet.springbackend.application.port.in.command.RefreshAuthenticationOutcome;
import pl.kondziet.springbackend.infrastructure.security.token.JwtFacade;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements AuthenticationUseCase {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final GenerateTokenUseCase generateTokenUseCase;
    private final JwtFacade jwtFacade;

    @Override
    public AuthenticationOutcome authenticate(AuthenticateCommand command) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        command.email(),
                        command.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(command.email());
        String generatedAccessToken = generateTokenUseCase.generateAccessToken(userDetails);
        String generatedRefreshToken = generateTokenUseCase.generateRefreshToken(userDetails);

        return AuthenticationOutcome.builder()
                .accessToken(generatedAccessToken)
                .refreshToken(generatedRefreshToken)
                .build();
    }

    @Override
    public RefreshAuthenticationOutcome refreshAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization Header");
        }
        String refreshToken = authorizationHeader.substring(7);
        String userEmail = jwtFacade.extractUserEmail(refreshToken);

        if (userEmail != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtFacade.isTokenValid(refreshToken, userDetails)) {
                String accessToken = generateTokenUseCase.generateAccessToken(userDetails);
                return RefreshAuthenticationOutcome.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }

        throw new IllegalArgumentException("Invalid Email in Refresh Token");
    }
}
