package pl.kondziet.springbackend.application.port.in;

import java.util.List;
import java.util.UUID;

public record CreateGroupExpenseCommand(String name, UUID groupId, List<DebtDetails> debtDetails) {
}
