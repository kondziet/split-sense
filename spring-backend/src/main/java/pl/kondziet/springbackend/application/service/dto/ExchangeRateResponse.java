package pl.kondziet.springbackend.application.exchangerate;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateResponse(Map<String, BigDecimal> data) {
}
