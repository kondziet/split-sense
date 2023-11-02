package pl.kondziet.springbackend.model.dto;

import pl.kondziet.springbackend.model.entity.ExpenseDebtor;

import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseDebtorRequest(UUID debtorId, String currency, BigDecimal amount) {
}
