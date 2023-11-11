package pl.kondziet.springbackend.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.adapter.in.web.dto.GroupRequest;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.application.domain.model.Group;
import pl.kondziet.springbackend.application.domain.model.UserId;
import pl.kondziet.springbackend.application.port.in.CreateGroupCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupUseCase;
import pl.kondziet.springbackend.application.port.out.LoadUserGroupsPort;
import pl.kondziet.springbackend.infrastructure.mapper.GroupMapper;
import pl.kondziet.springbackend.service.UserService;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final UserService userService;
    private final CreateGroupUseCase createGroupUseCase;
    private final LoadUserGroupsPort loadUserGroupsPort;
    private final GroupMapper groupMapper;


    @GetMapping
    ResponseEntity<?> getUserGroups(Authentication authentication) {
        UserJpaEntity authenticatedUserJpaEntity = userService.findByEmail(authentication.getName());

        Set<Group> groups = loadUserGroupsPort.loadGroups(
                new UserId(authenticatedUserJpaEntity.getId())
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(groupMapper.groupsToGroupResponses(groups));
    }

    @PostMapping
    ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest, Authentication authentication) {
        UserJpaEntity authenticatedUserJpaEntity = userService.findByEmail(authentication.getName());

        CreateGroupCommand command = CreateGroupCommand.builder()
                .groupName(groupRequest.name())
                .groupCurrency(groupRequest.currency())
                .groupOwnerId(new UserId(authenticatedUserJpaEntity.getId()))
                .build();

        createGroupUseCase.createGroup(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
