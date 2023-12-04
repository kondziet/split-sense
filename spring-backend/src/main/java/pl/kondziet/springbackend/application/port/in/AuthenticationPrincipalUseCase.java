package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

public interface AuthenticationPrincipalUseCase {

    UserId getAuthenticatedUserId();
}
