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
import pl.kondziet.springbackend.util.mapper.GroupMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private final GroupMapper groupMapper;

    @GetMapping
    ResponseEntity<?> getUserGroups(Authentication authentication) {
        User authenticatedUser = userService.findByEmail(authentication.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        groupMapper.groupsToDtos(
                                groupService.findAllUserGroups(authenticatedUser)
                        )
                );
    }

    @PostMapping
    ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest, Authentication authentication) {
        User authenticatedUser = userService.findByEmail(authentication.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        groupMapper.groupToDto(
                                groupService.createGroup(groupRequest, authenticatedUser)
                        )
                );
    }

}
