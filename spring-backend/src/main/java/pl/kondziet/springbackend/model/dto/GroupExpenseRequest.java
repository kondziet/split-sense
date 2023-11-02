package pl.kondziet.springbackend.model.dto;

import java.util.List;

public record GroupExpenseRequest(String name, List<ExpenseDebtorRequest> debts) {
}
