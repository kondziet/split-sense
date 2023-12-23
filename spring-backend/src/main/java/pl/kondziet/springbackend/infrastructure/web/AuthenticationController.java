package pl.kondziet.springbackend.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kondziet.springbackend.application.service.dto.SignInRequest;
import pl.kondziet.springbackend.infrastructure.security.token.AuthenticationService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(signInRequest));
    }
    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshToken(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authenticationService.refreshAuthentication(httpServletRequest));
    }
}
