package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.application.port.in.command.AuthenticateCommand;
import pl.kondziet.springbackend.application.port.in.command.AuthenticationOutcome;

public interface AuthenticateUseCase {

    AuthenticationOutcome authenticate(AuthenticateCommand command);
}
