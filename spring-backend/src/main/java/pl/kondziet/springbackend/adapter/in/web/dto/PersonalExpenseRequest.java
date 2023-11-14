package pl.kondziet.springbackend.adapter.in.web.dto;

import java.util.Set;

public record PersonalExpenseRequest(String name, Set<DebtRequest> debts) {
}
