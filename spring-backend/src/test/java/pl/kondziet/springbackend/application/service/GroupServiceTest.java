package pl.kondziet.springbackend.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.kondziet.springbackend.application.service.command.CreateGroupCommand;
import pl.kondziet.springbackend.domain.model.entity.Group;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@Sql(scripts = "classpath:script.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupServiceTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");
    @Autowired
    private GroupService groupService;

    @Test
    void createGroup() {
        UUID userId = UUID.fromString("a550559d-3c3c-4dbd-9a80-2c6b045b3e91");

        CreateGroupCommand command = CreateGroupCommand.builder()
                .groupName("Test group")
                .groupCurrency("PLN")
                .groupOwnerId(userId)
                .build();

        Group group = groupService.createGroup(command);
        List<Group> userGroups = groupService.loadUserGroups(userId);

        assertThat(group).isNotNull();
        assertThat(userGroups.stream().filter(g -> g.getId().equals(group.getId())).findFirst()).isPresent();
    }
}