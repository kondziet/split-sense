package pl.kondziet.springbackend.application.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DebtRequest(UUID debtorId, String currency, BigDecimal amount) {
}
