package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;

public interface SavePersonalExpensePort {

    void savePersonalExpense(PersonalExpense personalExpense);
}
