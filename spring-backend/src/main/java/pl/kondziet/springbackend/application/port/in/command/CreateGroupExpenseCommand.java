package pl.kondziet.springbackend.application.port.in.command;

import lombok.Builder;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.Set;

@Builder
public record CreateGroupExpenseCommand(String expenseName, UserId expensePayer, Set<DebtDetail> expenseDebtDetails, GroupId expenseGroupId) {
}
