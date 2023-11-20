package pl.kondziet.springbackend.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.Debt;
import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.port.in.command.CreateGroupExpenseCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupExpenseUseCase;
import pl.kondziet.springbackend.application.port.out.ExpenseOutputPort;
import pl.kondziet.springbackend.infrastructure.mapper.DebtMapper;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateGroupExpenseService implements CreateGroupExpenseUseCase {

    private final ExpenseOutputPort expenseOutputPort;
    private final DebtMapper debtMapper;

    @Override
    public void createGroupExpense(CreateGroupExpenseCommand command) {

        Set<Debt> expenseDebts = debtMapper.debtRequestsToDebts(command.expenseDebts());

        GroupExpense groupExpense = GroupExpense.builder()
                .name(command.expenseName())
                .creditorId(command.expensePayer())
                .debts(expenseDebts)
                .groupId(command.expenseGroupId())
                .build();

        expenseOutputPort.saveGroupExpense(groupExpense);

    }
}
