package pl.kondziet.springbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import pl.kondziet.springbackend.adapter.in.web.dto.*;

public interface AuthenticationService {
    public SignUpResponse register(SignUpRequest registerRequest);
    public SignInResponse authenticate(SignInRequest signInRequest);
    public TokenRefreshResponse refreshToken(HttpServletRequest request);

}
