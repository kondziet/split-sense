package pl.kondziet.springbackend.application.service.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateResponse(Map<String, BigDecimal> data) {
}
