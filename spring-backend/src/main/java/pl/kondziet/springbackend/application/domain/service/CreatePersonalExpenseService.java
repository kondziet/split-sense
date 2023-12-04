package pl.kondziet.springbackend.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.Debt;
import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;
import pl.kondziet.springbackend.application.port.in.CreatePersonalExpenseUseCase;
import pl.kondziet.springbackend.application.port.in.command.CreatePersonalExpenseCommand;
import pl.kondziet.springbackend.application.port.out.ExpensePersistencePort;
import pl.kondziet.springbackend.infrastructure.mapper.DebtMapper;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class CreatePersonalExpenseService implements CreatePersonalExpenseUseCase {

    private final ExpensePersistencePort expensePersistencePort;
    private final DebtMapper debtMapper;
    @Override
    public void createPersonalExpense(CreatePersonalExpenseCommand command) {

        Set<Debt> expenseDebts = debtMapper.debtRequestsToDebts(command.expenseDebts());

        PersonalExpense personalExpense = PersonalExpense.builder()
                .name(command.expenseName())
                .creditorId(command.expensePayer())
                .debts(expenseDebts)
                .build();

        expensePersistencePort.savePersonalExpense(personalExpense);
    }
}
