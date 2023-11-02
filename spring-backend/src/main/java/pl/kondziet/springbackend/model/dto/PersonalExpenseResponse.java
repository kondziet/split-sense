package pl.kondziet.springbackend.model.dto;

import java.util.List;
import java.util.UUID;

public record PersonalExpenseResponse(String name, UUID payerId, List<ExpenseDebtorResponse> debts) {
}
