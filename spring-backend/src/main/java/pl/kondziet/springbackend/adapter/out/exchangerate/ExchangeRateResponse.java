package pl.kondziet.springbackend.adapter.out.exchangerate;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateResponse(Map<String, BigDecimal> data) {
}
