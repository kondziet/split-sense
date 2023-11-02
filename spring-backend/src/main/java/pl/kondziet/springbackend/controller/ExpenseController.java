package pl.kondziet.springbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.model.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.model.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.service.ExpenseService;
import pl.kondziet.springbackend.service.UserService;
import pl.kondziet.springbackend.util.mapper.ExpenseMapper;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final ExpenseMapper expenseMapper;

    @PostMapping("/group/{groupId}")
    ResponseEntity<?> createGroupExpense(
            @PathVariable("groupId") UUID groupId,
            @RequestBody GroupExpenseRequest groupExpenseRequest,
            Authentication authentication
    ) {
        User authenticatedUser = userService.findByEmail(authentication.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        expenseMapper.groupExpenseToDto(
                                expenseService.createGroupExpense(groupExpenseRequest, authenticatedUser, groupId)
                        )
                );
    }

    @PostMapping("/personal")
    ResponseEntity<?> createPersonalExpense(
            @RequestBody PersonalExpenseRequest personalExpenseRequest,
            Authentication authentication
    ) {
        User authenticatedUser = userService.findByEmail(authentication.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        expenseMapper.personalExpenseToDto(
                                expenseService.createPersonalExpense(personalExpenseRequest, authenticatedUser)
                        )
                );
    }
}
