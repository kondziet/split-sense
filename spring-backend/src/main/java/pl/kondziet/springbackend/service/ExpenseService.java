package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.model.dto.ExpenseRequest;
import pl.kondziet.springbackend.model.entity.Expense;

import java.util.UUID;

public interface ExpenseService {

    Expense createExpense(ExpenseRequest expenseRequest, UUID groupId);
}
