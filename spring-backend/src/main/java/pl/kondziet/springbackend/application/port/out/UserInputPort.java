package pl.kondziet.springbackend.application.port.out;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserInputPort {
    UserDetails loadUserDetails(String email);
}
