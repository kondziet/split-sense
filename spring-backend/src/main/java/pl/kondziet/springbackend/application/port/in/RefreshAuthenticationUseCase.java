package pl.kondziet.springbackend.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import pl.kondziet.springbackend.domain.dto.TokenRefreshResponse;

public interface RefreshAuthenticationUseCase {
    TokenRefreshResponse refreshAuthentication(HttpServletRequest request);
}
