package pl.kondziet.springbackend.infrastructure.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.application.service.AuthenticationPrincipalService;
import pl.kondziet.springbackend.application.service.ExpenseService;
import pl.kondziet.springbackend.application.service.command.CreateGroupExpenseCommand;
import pl.kondziet.springbackend.application.service.command.CreatePersonalExpenseCommand;
import pl.kondziet.springbackend.application.service.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.application.service.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.infrastructure.security.userdetails.AppUserDetails;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final AuthenticationPrincipalService authenticationPrincipalService;
    private final ExpenseService expenseService;

    @PostMapping("/group/{groupId}")
    ResponseEntity<?> createGroupExpense(@PathVariable("groupId") UUID groupId, @RequestBody GroupExpenseRequest groupExpenseRequest) {
        AppUserDetails authenticatedUser = authenticationPrincipalService.getAuthenticatedUser();

        CreateGroupExpenseCommand command = CreateGroupExpenseCommand.builder()
                .expenseName(groupExpenseRequest.name())
                .expensePayer(authenticatedUser.getId())
                .expenseDebts(groupExpenseRequest.debts())
                .expenseGroupId(groupId)
                .build();

        expenseService.createGroupExpense(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/personal")
    ResponseEntity<?> createPersonalExpense(@RequestBody PersonalExpenseRequest personalExpenseRequest) {
        AppUserDetails authenticatedUser = authenticationPrincipalService.getAuthenticatedUser();

        CreatePersonalExpenseCommand command = CreatePersonalExpenseCommand.builder()
                .expenseName(personalExpenseRequest.name())
                .expensePayer(authenticatedUser.getId())
                .expenseDebts(personalExpenseRequest.debts())
                .build();

        expenseService.createPersonalExpense(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
