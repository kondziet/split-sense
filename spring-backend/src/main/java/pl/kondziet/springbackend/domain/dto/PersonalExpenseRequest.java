package pl.kondziet.springbackend.domain.dto;

import java.util.Set;

public record PersonalExpenseRequest(String name, Set<DebtRequest> debts) {
}
