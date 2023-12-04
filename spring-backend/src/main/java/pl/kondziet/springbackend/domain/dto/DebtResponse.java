package pl.kondziet.springbackend.domain.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DebtResponse(UUID debtorId, String currency, BigDecimal amount) {
}
