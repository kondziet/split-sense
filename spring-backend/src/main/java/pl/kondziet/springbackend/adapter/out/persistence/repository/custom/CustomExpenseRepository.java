package pl.kondziet.springbackend.adapter.out.persistence.repository.custom;

import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;

public interface CustomExpenseRepository {

    void save(GroupExpense groupExpense);
    void save(PersonalExpense personalExpense);
}
