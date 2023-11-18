package pl.kondziet.springbackend.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import pl.kondziet.springbackend.adapter.in.web.dto.TokenRefreshResponse;

public interface RefreshAuthenticationUseCase {
    TokenRefreshResponse refreshAuthentication(HttpServletRequest request);
}
