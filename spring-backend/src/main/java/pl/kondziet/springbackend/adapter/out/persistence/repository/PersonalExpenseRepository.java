package pl.kondziet.springbackend.adapter.out.persistence.repository;

import pl.kondziet.springbackend.application.domain.model.entity.PersonalExpense;

public interface PersonalExpenseRepository {
    void save(PersonalExpense personalExpense);
}
