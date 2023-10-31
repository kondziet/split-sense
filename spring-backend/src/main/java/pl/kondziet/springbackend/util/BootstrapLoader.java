package pl.kondziet.springbackend.util;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.model.entity.UserGroup;
import pl.kondziet.springbackend.service.GroupService;
import pl.kondziet.springbackend.service.UserGroupService;
import pl.kondziet.springbackend.service.UserService;

@AllArgsConstructor
@Component
public class BootstrapLoader implements CommandLineRunner {

    private final UserService userService;
    private final GroupService groupService;
    private final UserGroupService userGroupService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder()
                .username("dwight")
                .email("dwight@gmail.com")
                .password(passwordEncoder.encode("beetroot"))
                .build();

        userService.save(user1);

        Group group1 = Group.builder()
                .name("farm lovers")
                .currency("USD")
                .build();

        groupService.save(group1);

        UserGroup userGroup1 = UserGroup.builder()
                .id(UserGroup.UserGroupId.builder()
                        .userId(user1.getId())
                        .groupId(group1.getId())
                        .build())
                .user(user1)
                .group(group1)
                .build();

        userGroupService.save(userGroup1);

    }
}
