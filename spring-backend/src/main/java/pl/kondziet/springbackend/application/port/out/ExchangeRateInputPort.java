package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.ExchangeRate;

public interface ExchangeRateInputPort {

    ExchangeRate loadExchangeRate(String baseCurrency, String targetCurrency);
}
