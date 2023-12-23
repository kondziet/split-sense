package pl.kondziet.springbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.application.service.GroupMembershipService;
import pl.kondziet.springbackend.application.service.GroupService;
import pl.kondziet.springbackend.application.service.UserService;
import pl.kondziet.springbackend.application.service.command.CreateGroupCommand;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.domain.model.entity.User;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BootLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupMembershipService groupMembershipService;

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

        User savedUser1 = userService.save(user1);
        User savedUser2 = userService.save(user2);

        groupService.createGroup(CreateGroupCommand.builder()
                .groupName("farm lovers")
                .groupCurrency("USD")
                .groupOwnerId(user1.getId())
                .build());

        List<Group> groups = groupService.loadUserGroups(user1.getId());

        groupMembershipService.save(GroupMembership.builder()
                .id(GroupMembership.UserGroupId.builder()
                        .userId(savedUser2.getId())
                        .groupId(groups.stream().findFirst().get().getId())
                        .build())
                .group(groups.stream().findFirst().get())
                .user(savedUser2)
                .build());
    }
}
