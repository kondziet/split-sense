package pl.kondziet.springbackend.application.domain.dto;

import java.util.Set;

public record GroupExpenseRequest(String name, Set<DebtRequest> debts) {
}
