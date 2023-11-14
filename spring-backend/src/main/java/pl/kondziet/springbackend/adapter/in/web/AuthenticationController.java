package pl.kondziet.springbackend.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.adapter.in.web.dto.SignInRequest;
import pl.kondziet.springbackend.application.port.in.AuthenticateUseCase;
import pl.kondziet.springbackend.application.port.in.RefreshAuthenticationUseCase;
import pl.kondziet.springbackend.application.port.in.command.AuthenticateCommand;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticateUseCase authenticateUseCase;
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase;

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        AuthenticateCommand command = AuthenticateCommand.builder()
                .email(signInRequest.email())
                .password(signInRequest.password())
                .build();

        return ResponseEntity.ok(authenticateUseCase.authenticate(command));
    }
    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(refreshAuthenticationUseCase.refreshAuthentication(request));
    }
}
