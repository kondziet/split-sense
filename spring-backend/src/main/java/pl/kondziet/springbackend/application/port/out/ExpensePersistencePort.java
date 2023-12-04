package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.domain.model.entity.PersonalExpense;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;

import java.util.List;

public interface ExpensePersistencePort {

    List<GroupExpense> loadGroupExpenses(GroupId groupId);
    void saveGroupExpense(GroupExpense groupExpense);
    void savePersonalExpense(PersonalExpense personalExpense);
}
