package pl.kondziet.springbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.model.dto.GroupRequest;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.model.entity.UserGroup;
import pl.kondziet.springbackend.service.GroupService;
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
        User user = userService.findByEmail(authentication.getName());

        return ResponseEntity.ok(
                groupService
                        .findAllUserGroups(user)
                        .stream()
                        .map(groupMapper::groupToDto)
                        .toList()
        );
    }

    @PostMapping
    ResponseEntity<?> createGroup(Authentication authentication, @RequestBody GroupRequest groupRequest) {
        User owner = userService.findByEmail(authentication.getName());
        Group group = groupMapper.dtoToGroup(groupRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        groupMapper.groupToDto(
                                groupService.createNewGroup(group, owner)
                        )
                );
    }

}