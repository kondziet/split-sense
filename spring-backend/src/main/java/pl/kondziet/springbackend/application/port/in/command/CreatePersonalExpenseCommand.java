package pl.kondziet.springbackend.application.port.in.command;

import lombok.Builder;
import pl.kondziet.springbackend.application.domain.dto.DebtRequest;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.Set;

@Builder
public record CreatePersonalExpenseCommand(String expenseName, UserId expensePayer, Set<DebtRequest> expenseDebts) {
}
