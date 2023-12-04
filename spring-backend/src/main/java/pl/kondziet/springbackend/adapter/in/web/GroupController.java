package pl.kondziet.springbackend.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.adapter.out.persistence.GroupPersistenceAdapter;
import pl.kondziet.springbackend.application.domain.dto.GroupRequest;
import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.in.AuthenticationPrincipalUseCase;
import pl.kondziet.springbackend.application.port.in.command.CreateGroupCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupUseCase;
import pl.kondziet.springbackend.application.port.out.GroupPersistencePort;
import pl.kondziet.springbackend.infrastructure.mapper.GroupMapper;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final AuthenticationPrincipalUseCase authenticationPrincipalUseCase;
    private final CreateGroupUseCase createGroupUseCase;
    private final GroupPersistencePort groupPersistencePort;
    private final GroupMapper groupMapper;
    private final GroupPersistenceAdapter groupPersistenceAdapter;


    @GetMapping
    ResponseEntity<?> getUserGroups() {
        UserId authenticatedUserId = authenticationPrincipalUseCase.getAuthenticatedUserId();

        List<Group> groups = groupPersistencePort.loadUserGroups(authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(groupMapper.groupsToGroupResponses(groups));
    }

    @PostMapping
    ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        UserId authenticatedUserId = authenticationPrincipalUseCase.getAuthenticatedUserId();

        CreateGroupCommand command = CreateGroupCommand.builder()
                .groupName(groupRequest.name())
                .groupCurrency(groupRequest.currency())
                .groupOwnerId(authenticatedUserId)
                .build();

        createGroupUseCase.createGroup(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}