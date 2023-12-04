package pl.kondziet.springbackend.domain.dto;

import java.util.Set;

public record GroupExpenseRequest(String name, Set<DebtRequest> debts) {
}
