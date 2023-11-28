package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupExpenseRepository;
import pl.kondziet.springbackend.adapter.out.persistence.repository.PersonalExpenseRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.port.out.ExpenseInputPort;
import pl.kondziet.springbackend.application.port.out.ExpenseOutputPort;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class ExpensePersistenceAdapter implements ExpenseOutputPort, ExpenseInputPort {

    private final GroupExpenseRepository groupExpenseRepository;
    private final PersonalExpenseRepository personalExpenseRepository;

    @Override
    public void saveGroupExpense(GroupExpense groupExpense) {
        groupExpenseRepository.save(groupExpense);
    }

    @Override
    public void savePersonalExpense(PersonalExpense personalExpense) {
        personalExpenseRepository.save(personalExpense);
    }

    @Override
    public List<GroupExpense> loadGroupExpenses(GroupId groupId) {
        return groupExpenseRepository.findAllExpenses(groupId);
    }
}
