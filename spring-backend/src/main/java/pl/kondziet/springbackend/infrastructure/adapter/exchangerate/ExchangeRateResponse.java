package pl.kondziet.springbackend.infrastructure.adapter.exchangerate;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateResponse(Map<String, BigDecimal> data) {
}
