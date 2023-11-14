package pl.kondziet.springbackend.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import pl.kondziet.springbackend.application.port.in.command.RefreshAuthenticationOutcome;

public interface RefreshAuthenticationUseCase {

    public RefreshAuthenticationOutcome refreshAuthentication(HttpServletRequest request);
}
