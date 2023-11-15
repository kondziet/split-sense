package pl.kondziet.springbackend.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import pl.kondziet.springbackend.application.port.in.command.AuthenticateCommand;
import pl.kondziet.springbackend.application.port.in.command.AuthenticationOutcome;
import pl.kondziet.springbackend.application.port.in.command.RefreshAuthenticationOutcome;

public interface AuthenticationUseCase {

    AuthenticationOutcome authenticate(AuthenticateCommand command);
    public RefreshAuthenticationOutcome refreshAuthentication(HttpServletRequest request);
}
