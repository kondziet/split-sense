package pl.kondziet.springbackend.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.application.service.AuthenticationPrincipalService;
import pl.kondziet.springbackend.application.service.GroupService;
import pl.kondziet.springbackend.application.service.command.CreateGroupCommand;
import pl.kondziet.springbackend.application.service.dto.GroupRequest;
import pl.kondziet.springbackend.application.service.dto.GroupResponse;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.infrastructure.security.userdetails.AppUserDetails;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final AuthenticationPrincipalService authenticationPrincipalService;
    private final GroupService groupService;


    @GetMapping
    ResponseEntity<?> getUserGroups() {
        AppUserDetails authenticatedUser = authenticationPrincipalService.getAuthenticatedUser();

        List<Group> groups = groupService.loadUserGroups(authenticatedUser.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(groups.stream()
                        .map(group -> new GroupResponse(group.getName(), group.getCurrency()))
                        .toList()
                );
    }

    @PostMapping
    ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        AppUserDetails authenticatedUser = authenticationPrincipalService.getAuthenticatedUser();

        CreateGroupCommand command = CreateGroupCommand.builder()
                .groupName(groupRequest.name())
                .groupCurrency(groupRequest.currency())
                .groupOwnerId(authenticatedUser.getId())
                .build();

        groupService.createGroup(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}