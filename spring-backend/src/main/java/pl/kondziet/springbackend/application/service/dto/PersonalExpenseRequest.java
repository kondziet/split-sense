package pl.kondziet.springbackend.application.service.dto;

import java.util.Set;

public record PersonalExpenseRequest(String name, Set<DebtRequest> debts) {
}
