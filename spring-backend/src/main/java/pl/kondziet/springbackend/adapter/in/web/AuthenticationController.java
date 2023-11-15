package pl.kondziet.springbackend.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.adapter.in.web.dto.SignInRequest;
import pl.kondziet.springbackend.application.port.in.AuthenticationUseCase;
import pl.kondziet.springbackend.application.port.in.command.AuthenticateCommand;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        AuthenticateCommand command = AuthenticateCommand.builder()
                .email(signInRequest.email())
                .password(signInRequest.password())
                .build();

        return ResponseEntity.ok(authenticationUseCase.authenticate(command));
    }
    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationUseCase.refreshAuthentication(request));
    }
}
