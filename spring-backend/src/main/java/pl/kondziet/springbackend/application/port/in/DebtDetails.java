package pl.kondziet.springbackend.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public record DebtDetails(UUID debtorId, String currency, BigDecimal amount) {
}
