package pl.kondziet.springbackend.adapter.out.persistence.repository;

import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;

public interface GroupExpenseRepository {
    void save(GroupExpense groupExpense);
}
