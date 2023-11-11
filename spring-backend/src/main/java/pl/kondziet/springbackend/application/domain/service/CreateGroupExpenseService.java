package pl.kondziet.springbackend.application.domain.service;

import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.port.in.CreateGroupExpenseCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupExpenseUseCase;

@Service
public class CreateGroupExpenseService implements CreateGroupExpenseUseCase {
    @Override
    public void createGroupExpense(CreateGroupExpenseCommand command) {

    }
}
