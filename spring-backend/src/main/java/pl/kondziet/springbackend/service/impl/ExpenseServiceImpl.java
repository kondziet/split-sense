package pl.kondziet.springbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.dto.ExpenseRequest;
import pl.kondziet.springbackend.model.entity.Expense;
import pl.kondziet.springbackend.service.ExpenseService;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Override
    public Expense createExpense(ExpenseRequest expenseRequest, UUID groupId) {
        return null;
    }
}
