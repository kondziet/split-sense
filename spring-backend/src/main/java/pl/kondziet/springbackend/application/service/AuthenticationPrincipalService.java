package pl.kondziet.springbackend.application.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.infrastructure.security.userdetails.AppUserDetails;

@Service
@Transactional
public class AuthenticationPrincipalService {

    public AppUserDetails getAuthenticatedUser() {
        return (AppUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
