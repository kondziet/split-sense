package pl.kondziet.springbackend.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.adapter.in.web.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.adapter.in.web.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.application.port.in.CreateGroupExpenseUseCase;
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
            @RequestBody GroupExpenseRequest expenseDetails,
            Authentication authentication
    ) {
        User authenticatedUser = userService.findByEmail(authentication.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        expenseMapper.groupExpenseToDto(
                                expenseService.createGroupExpense(expenseDetails, authenticatedUser, groupId)
                        )
                );
    }

    @PostMapping("/personal")
    ResponseEntity<?> createPersonalExpense(
            @RequestBody PersonalExpenseRequest expenseDetails,
            Authentication authentication
    ) {
        User authenticatedUser = userService.findByEmail(authentication.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        expenseMapper.personalExpenseToDto(
                                expenseService.createPersonalExpense(expenseDetails, authenticatedUser)
                        )
                );
    }
}
