package pl.kondziet.springbackend.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseDebtorResponse(UUID debtorId, String currency, BigDecimal amount) {
}
