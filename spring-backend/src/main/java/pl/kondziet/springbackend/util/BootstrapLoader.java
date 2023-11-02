package pl.kondziet.springbackend.util;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.model.dto.GroupRequest;
import pl.kondziet.springbackend.model.entity.Expense;
import pl.kondziet.springbackend.model.entity.GroupExpense;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.repository.UserRepository;
import pl.kondziet.springbackend.service.GroupService;
import pl.kondziet.springbackend.service.UserService;

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
