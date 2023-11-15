package pl.kondziet.springbackend.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.in.AuthenticationPrincipalUseCase;

@Service
public class AuthenticationPrincipalService implements AuthenticationPrincipalUseCase {
    @Override
    public UserId getAuthenticatedUserId() {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }
}
