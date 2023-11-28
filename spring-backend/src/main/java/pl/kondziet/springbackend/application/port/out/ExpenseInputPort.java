package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;

import java.util.List;

public interface ExpenseInputPort {

    List<GroupExpense> loadGroupExpenses(GroupId groupId);
}
