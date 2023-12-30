package pl.kondziet.springbackend.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.kondziet.springbackend.application.service.command.CreateGroupCommand;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupServiceTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @Test
    void createGroupAndAssignOwner() {
        User savedUsed = userService.save(User.builder()
                .username("test")
                .password("test")
                .email("test@test.com")
                .build());
        CreateGroupCommand command = CreateGroupCommand.builder()
                .groupName("test group")
                .groupCurrency("PLN")
                .groupOwnerId(savedUsed.getId())
                .build();

        Group group = groupService.createGroup(command);
        List<Group> userGroups = groupService.loadUserGroups(savedUsed.getId());

        assertThat(group).isNotNull();
        assertThat(userGroups.stream().filter(g -> g.getId().equals(group.getId())).findFirst()).isPresent();
    }
}