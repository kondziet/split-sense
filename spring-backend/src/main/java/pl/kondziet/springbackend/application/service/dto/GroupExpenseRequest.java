package pl.kondziet.springbackend.application.service.dto;

import java.util.Set;

public record GroupExpenseRequest(String name, Set<DebtRequest> debts) {
}
