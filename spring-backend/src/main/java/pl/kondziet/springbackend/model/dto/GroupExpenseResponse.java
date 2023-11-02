package pl.kondziet.springbackend.model.dto;

import java.util.List;
import java.util.UUID;

public record GroupExpenseResponse(String name, UUID payerId, List<ExpenseDebtorResponse> debts) {
}
