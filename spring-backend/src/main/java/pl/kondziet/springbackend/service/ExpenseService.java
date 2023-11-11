package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.adapter.in.web.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.adapter.in.web.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupExpenseJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.PersonalExpenseJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;

import java.util.UUID;

public interface ExpenseService {

    PersonalExpenseJpaEntity createPersonalExpense(PersonalExpenseRequest expenseDetails, UserJpaEntity payer);
}
