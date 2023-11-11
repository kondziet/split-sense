package pl.kondziet.springbackend.adapter.in.web.dto;

import java.util.List;
import java.util.UUID;

public record PersonalExpenseResponse(String name, UUID payerId, List<DebtResponse> debts) {
}
