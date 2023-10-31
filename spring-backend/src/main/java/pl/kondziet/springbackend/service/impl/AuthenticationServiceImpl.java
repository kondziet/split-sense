package pl.kondziet.springbackend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.dto.*;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.security.token.JwtService;
import pl.kondziet.springbackend.security.token.TokenService;
import pl.kondziet.springbackend.service.AuthenticationService;
import pl.kondziet.springbackend.service.UserService;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
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

        userService.save(user);
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

        User user = userService.findByEmail(signInRequest.getEmail());
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
            UserDetails userDetails = userService.findByEmail(userEmail);
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