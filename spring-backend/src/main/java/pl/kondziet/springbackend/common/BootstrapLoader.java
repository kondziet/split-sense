package pl.kondziet.springbackend.common;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.UserRepository;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.in.command.CreateGroupCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupUseCase;

@AllArgsConstructor
@Component
public class BootstrapLoader implements CommandLineRunner {

    private final CreateGroupUseCase createGroupUseCase;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        UserJpaEntity userJpaEntity1 = UserJpaEntity.builder()
                .username("dwight")
                .email("dwight@gmail.com")
                .password(passwordEncoder.encode("beetroot"))
                .build();
        UserJpaEntity userJpaEntity2 = UserJpaEntity.builder()
                .username("kelly")
                .email("kelly@gmail.com")
                .password(passwordEncoder.encode("cookies"))
                .build();

        UserJpaEntity savedUser1 = userRepository.save(userJpaEntity1);
        UserJpaEntity savedUser2 = userRepository.save(userJpaEntity2);

        CreateGroupCommand command = CreateGroupCommand.builder()
                .groupName("farm lovers")
                .groupCurrency("USD")
                .groupOwnerId(new UserId(savedUser1.getId()))
                .build();

        createGroupUseCase.createGroup(command);

    }
}
