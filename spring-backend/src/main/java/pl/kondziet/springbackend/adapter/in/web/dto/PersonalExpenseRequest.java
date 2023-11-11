package pl.kondziet.springbackend.adapter.in.web.dto;

import java.util.List;

public record PersonalExpenseRequest(String name, List<DebtRequest> debts) {
}
