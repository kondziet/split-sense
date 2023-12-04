package pl.kondziet.springbackend.infrastructure.adapter.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.domain.dto.SignInRequest;
import pl.kondziet.springbackend.application.port.in.AuthenticationUseCase;
import pl.kondziet.springbackend.application.port.in.RefreshAuthenticationUseCase;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase;

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationUseCase.authenticate(signInRequest));
    }
    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshToken(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(refreshAuthenticationUseCase.refreshAuthentication(httpServletRequest));
    }
}
