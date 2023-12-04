package pl.kondziet.springbackend.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.GroupExpenseJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.repository.ExpenseRepository;
import pl.kondziet.springbackend.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.domain.model.entity.PersonalExpense;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;
import pl.kondziet.springbackend.application.port.out.ExpensePersistencePort;
import pl.kondziet.springbackend.infrastructure.mapper.ExpenseMapper;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ExpensePersistenceAdapter implements ExpensePersistencePort {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public void saveGroupExpense(GroupExpense groupExpense) {
        expenseRepository.save(
                expenseMapper.groupExpenseToGroupExpenseJpaEntity(groupExpense)
        );
    }

    @Override
    public void savePersonalExpense(PersonalExpense personalExpense) {
        expenseRepository.save(
                expenseMapper.personalExpenseToPersonalExpenseJpaEntity(personalExpense)
        );
    }

    @Override
    public List<GroupExpense> loadGroupExpenses(GroupId groupId) {
        List<GroupExpenseJpaEntity> allByGroupJpaEntityId = expenseRepository.findAllByGroupJpaEntity_Id(groupId.id());

        return expenseMapper.groupExpenseJpaEntitiesToGroupExpenses(allByGroupJpaEntityId);
    }
}
