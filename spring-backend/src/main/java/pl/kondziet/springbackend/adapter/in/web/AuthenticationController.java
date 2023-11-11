package pl.kondziet.springbackend.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.adapter.in.web.dto.SignInRequest;
import pl.kondziet.springbackend.service.AuthenticationService;

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
    ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
