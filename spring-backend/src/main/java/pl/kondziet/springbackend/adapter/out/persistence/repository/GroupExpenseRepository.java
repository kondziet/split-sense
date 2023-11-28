package pl.kondziet.springbackend.adapter.out.persistence.repository;

import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;

import java.util.List;

public interface GroupExpenseRepository {
    void save(GroupExpense groupExpense);
    List<GroupExpense> findAllExpenses(GroupId groupId);
}
