package pl.kondziet.springbackend.application.domain.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.in.AuthenticationPrincipalUseCase;

@Service
public class AuthenticationPrincipalService implements AuthenticationPrincipalUseCase {
    @Override
    public UserId getAuthenticatedUserId() {
        UserJpaEntity principal = (UserJpaEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserId(principal.getId());
    }
}
