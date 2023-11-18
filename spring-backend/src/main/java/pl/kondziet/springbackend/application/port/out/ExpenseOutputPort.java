package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;

public interface ExpenseOutputPort {
    void saveGroupExpense(GroupExpense groupExpense);
    void savePersonalExpense(PersonalExpense personalExpense);
}
