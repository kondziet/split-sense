package pl.kondziet.springbackend.domain.model.valueobjects;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ExchangeRate(String baseCurrency, String targetCurrency, BigDecimal rate) {
}
