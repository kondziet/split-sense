package pl.kondziet.springbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.application.service.UserService;
import pl.kondziet.springbackend.domain.model.entity.User;

@RequiredArgsConstructor
@Component
public class BootLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder()
                .username("dwight")
                .email("dwight@gmail.com")
                .password(passwordEncoder.encode("beetroot"))
                .build();

        userService.save(user1);
    }
}
