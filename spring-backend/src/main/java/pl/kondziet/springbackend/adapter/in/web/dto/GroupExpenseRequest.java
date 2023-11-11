package pl.kondziet.springbackend.adapter.in.web.dto;

import java.util.List;

public record GroupExpenseRequest(String name, List<DebtRequest> debts) {
}
