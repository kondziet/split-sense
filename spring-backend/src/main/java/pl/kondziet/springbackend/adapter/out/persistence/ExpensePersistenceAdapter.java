package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.repository.ExpenseRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;
import pl.kondziet.springbackend.application.port.out.ExpenseOutputPort;

@RequiredArgsConstructor
@Component
public class ExpensePersistenceAdapter implements ExpenseOutputPort {

    private final ExpenseRepository expenseRepository;

    @Override
    public void saveGroupExpense(GroupExpense groupExpense) {
        expenseRepository.save(groupExpense);
    }

    @Override
    public void savePersonalExpense(PersonalExpense personalExpense) {
        expenseRepository.save(personalExpense);
    }
}
