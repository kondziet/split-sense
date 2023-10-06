package pl.kondziet.springbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.DTO.SignInRequest;
import pl.kondziet.springbackend.model.DTO.SignInResponse;
import pl.kondziet.springbackend.model.DTO.SignUpRequest;
import pl.kondziet.springbackend.model.DTO.SignUpResponse;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.repository.UserRepository;
import pl.kondziet.springbackend.security.token.TokenService;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

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
        String JwtToken = tokenService.generateToken(user);
        return SignInResponse.builder()
                .token(JwtToken)
                .build();
    }

}
