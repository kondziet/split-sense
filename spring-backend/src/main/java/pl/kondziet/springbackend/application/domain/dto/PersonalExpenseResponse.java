package pl.kondziet.springbackend.application.domain.dto;

import java.util.List;
import java.util.UUID;

public record PersonalExpenseResponse(String name, UUID payerId, List<DebtResponse> debts) {
}
