package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.application.port.in.command.CreateGroupExpenseCommand;

public interface CreateGroupExpenseUseCase {

    void createGroupExpense(CreateGroupExpenseCommand command);
}
