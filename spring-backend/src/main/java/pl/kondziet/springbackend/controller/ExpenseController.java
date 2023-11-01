package pl.kondziet.springbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.model.dto.ExpenseRequest;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.service.ExpenseService;
import pl.kondziet.springbackend.service.UserService;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    @GetMapping("/group/{groupId}")
    ResponseEntity<?> getGroupExpenses(
            @PathVariable("groupId") UUID groupId,
            @RequestBody ExpenseRequest expenseRequest,
            Authentication authentication
    ) {
        User authenticatedUser = userService.findByEmail(authentication.getName());

        return ResponseEntity.ok(groupId.toString());
    }
}
