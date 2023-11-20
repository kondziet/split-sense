package pl.kondziet.springbackend.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kondziet.springbackend.application.domain.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.application.domain.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.in.AuthenticationPrincipalUseCase;
import pl.kondziet.springbackend.application.port.in.CreatePersonalExpenseUseCase;
import pl.kondziet.springbackend.application.port.in.command.CreateGroupExpenseCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupExpenseUseCase;
import pl.kondziet.springbackend.application.port.in.command.CreatePersonalExpenseCommand;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final AuthenticationPrincipalUseCase authenticationPrincipalUseCase;
    private final CreateGroupExpenseUseCase createGroupExpenseUseCase;
    private final CreatePersonalExpenseUseCase createPersonalExpenseUseCase;

    @PostMapping("/group/{groupId}")
    ResponseEntity<?> createGroupExpense(@PathVariable("groupId") UUID groupId, @RequestBody GroupExpenseRequest groupExpenseRequest) {
        UserId authenticatedUserId = authenticationPrincipalUseCase.getAuthenticatedUserId();
        GroupId expenseGroupId = new GroupId(groupId);

        CreateGroupExpenseCommand command = CreateGroupExpenseCommand.builder()
                .expenseName(groupExpenseRequest.name())
                .expensePayer(authenticatedUserId)
                .expenseDebts(groupExpenseRequest.debts())
                .expenseGroupId(expenseGroupId)
                .build();

        createGroupExpenseUseCase.createGroupExpense(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/personal")
    ResponseEntity<?> createPersonalExpense(@RequestBody PersonalExpenseRequest personalExpenseRequest) {
        UserId authenticatedUserId = authenticationPrincipalUseCase.getAuthenticatedUserId();

        CreatePersonalExpenseCommand command = CreatePersonalExpenseCommand.builder()
                .expenseName(personalExpenseRequest.name())
                .expensePayer(authenticatedUserId)
                .expenseDebts(personalExpenseRequest.debts())
                .build();

        createPersonalExpenseUseCase.createPersonalExpense(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
