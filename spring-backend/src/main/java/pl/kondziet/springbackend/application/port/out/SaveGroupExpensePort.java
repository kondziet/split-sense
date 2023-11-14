package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;

public interface SaveGroupExpensePort {

    void saveGroupExpense(GroupExpense groupExpense);
}
