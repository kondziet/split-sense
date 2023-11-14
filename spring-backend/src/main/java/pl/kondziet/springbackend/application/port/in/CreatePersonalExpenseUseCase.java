package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.application.port.in.command.CreatePersonalExpenseCommand;

public interface CreatePersonalExpenseUseCase {

    void createPersonalExpense(CreatePersonalExpenseCommand command);
}
