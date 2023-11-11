package pl.kondziet.springbackend.util;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.UserRepository;
import pl.kondziet.springbackend.application.domain.model.UserId;
import pl.kondziet.springbackend.application.port.in.CreateGroupCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupUseCase;

@AllArgsConstructor
@Component
public class BootstrapLoader implements CommandLineRunner {

    private final GroupService groupService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder()
                .username("dwight")
                .email("dwight@gmail.com")
                .password(passwordEncoder.encode("beetroot"))
                .build();
        User user2 = User.builder()
                .username("kelly")
                .email("kelly@gmail.com")
                .password(passwordEncoder.encode("cookies"))
                .build();

        userRepository.save(user2);

        GroupRequest group1 = GroupRequest.builder()
                .name("farm lovers")
                .currency("USD")
                .build();

        groupService.createGroup(group1, user1);

    }
}
