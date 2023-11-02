package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.model.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.model.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.model.entity.GroupExpense;
import pl.kondziet.springbackend.model.entity.PersonalExpense;
import pl.kondziet.springbackend.model.entity.User;

import java.util.UUID;

public interface ExpenseService {

    GroupExpense createGroupExpense(GroupExpenseRequest groupExpenseRequest, User payer, UUID groupId);
    PersonalExpense createPersonalExpense(PersonalExpenseRequest personalExpenseRequest, User payer);
}
