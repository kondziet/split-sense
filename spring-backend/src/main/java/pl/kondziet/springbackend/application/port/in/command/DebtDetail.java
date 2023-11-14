package pl.kondziet.springbackend.application.port.in.command;

import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.math.BigDecimal;

public record DebtDetail(UserId debtorId, String currency, BigDecimal amount) {
}
