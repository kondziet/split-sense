package pl.kondziet.springbackend.application.port.in.command;

import lombok.Builder;
import pl.kondziet.springbackend.domain.dto.DebtRequest;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

import java.util.Set;

@Builder
public record CreatePersonalExpenseCommand(String expenseName, UserId expensePayer, Set<DebtRequest> expenseDebts) {
}
