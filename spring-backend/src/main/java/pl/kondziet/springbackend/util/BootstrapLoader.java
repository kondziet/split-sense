package pl.kondziet.springbackend.util;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.repository.UserRepository;

@AllArgsConstructor
@Component
public class BootstrapLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder()
                .username("kondziet")
                .email("kondziet@gmail.com")
                .password(passwordEncoder.encode("Siema eniu"))
                .build();

        userRepository.save(user1);

    }
}
