package pl.kondziet.springbackend.application.service.command;

import lombok.Builder;
import pl.kondziet.springbackend.application.service.dto.DebtRequest;

import java.util.Set;
import java.util.UUID;

@Builder
public record CreatePersonalExpenseCommand(String expenseName, UUID expensePayer, Set<DebtRequest> expenseDebts) {
}
