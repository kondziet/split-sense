package pl.kondziet.springbackend.application.domain.dto;

import java.util.Set;

public record PersonalExpenseRequest(String name, Set<DebtRequest> debts) {
}
