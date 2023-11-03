package pl.kondziet.springbackend.model.dto;

import java.util.List;

public record PersonalExpenseRequest(String name, List<DebtRequest> debts) {
}
