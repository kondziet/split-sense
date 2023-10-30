package pl.kondziet.springbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        return ResponseEntity.ok(
                groupService
                        .findAllUserGroups(userService.findByEmail(authentication.getName()))
                        .stream()
                        .map(groupMapper::groupToDto)
                        .toList()
        );
    }
}
