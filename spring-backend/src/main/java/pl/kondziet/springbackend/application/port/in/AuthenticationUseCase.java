package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.domain.dto.SignInRequest;
import pl.kondziet.springbackend.domain.dto.SignInResponse;

public interface AuthenticationUseCase {

    SignInResponse authenticate(SignInRequest signInRequest);
}
