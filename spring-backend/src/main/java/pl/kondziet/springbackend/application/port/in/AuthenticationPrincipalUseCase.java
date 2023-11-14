package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.application.domain.model.id.UserId;

public interface AuthenticationPrincipalUseCase {

    UserId getAuthenticatedUserId();
}
