package pl.kondziet.springbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.DTO.*;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.repository.UserRepository;
import pl.kondziet.springbackend.security.token.JwtService;
import pl.kondziet.springbackend.security.token.TokenService;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public SignUpResponse register(SignUpRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);
        return SignUpResponse.builder()
                .message("success")
                .build();
    }

    public SignInResponse authenticate(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow();
        String generatedAccessToken = tokenService.generateAccessToken(user);
        String generatedRefreshToken = tokenService.generateRefreshToken(user);

        return SignInResponse.builder()
                .accessToken(generatedAccessToken)
                .refreshToken(generatedRefreshToken)
                .build();
    }

    public TokenRefreshResponse refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization Header");
        }
        String refreshToken = authorizationHeader.substring(7);
        String userEmail = jwtService.extractUserEmail(refreshToken);

        if (userEmail != null) {
            UserDetails userDetails = userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                String accessToken = tokenService.generateAccessToken(userDetails);
                return TokenRefreshResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }

        throw new IllegalArgumentException("Invalid Email in Refresh Token");
    }

}
