package pl.kondziet.springbackend.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import pl.kondziet.springbackend.adapter.in.web.dto.SignInRequest;
import pl.kondziet.springbackend.adapter.in.web.dto.SignInResponse;
import pl.kondziet.springbackend.adapter.in.web.dto.TokenRefreshResponse;

public interface AuthenticationUseCase {

    SignInResponse authenticate(SignInRequest signInRequest);
}
