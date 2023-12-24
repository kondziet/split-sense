package pl.kondziet.springbackend.infrastructure.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.kondziet.springbackend.application.service.dto.ExchangeRateResponse;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ExchangeRateFacade {

    private final RestClient restClient;
    private final String apiKey;
    private final Map<Pair<String, String>, Pair<ExchangeRate, LocalDateTime>> exchangeRateCache = new HashMap<>();
    private final Duration cacheDuration = Duration.ofMinutes(1);

    public ExchangeRateFacade(@Value("${exchange-rate.api-url}") String apiUrl,
                              @Value("${exchange-rate.api-key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();
        this.apiKey = apiKey;
    }

    public ExchangeRate loadExchangeRate(String baseCurrency, String targetCurrency) {
        Pair<String, String> currencyPair = Pair.of(baseCurrency, targetCurrency);
        Pair<ExchangeRate, LocalDateTime> cached = getExchangeRateFromCache(currencyPair);
        if (Objects.isNull(cached) || isExpired(cached.getRight())) {
            ExchangeRate exchangeRate = getExchangeRateFromApi(currencyPair);
            exchangeRateCache.put(currencyPair, Pair.of(exchangeRate, LocalDateTime.now()));
            return exchangeRate;
        } else {
            return cached.getLeft();
        }
    }

    private ExchangeRate getExchangeRateFromApi(Pair<String, String> currencyPair) {
        ExchangeRateResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("apikey", apiKey)
                        .queryParam("base_currency", currencyPair.getLeft())
                        .queryParam("currencies", currencyPair.getRight())
                        .build()
                )
                .retrieve()
                .body(ExchangeRateResponse.class);

        return new ExchangeRate(currencyPair.getLeft(), currencyPair.getRight(), response.data().get(currencyPair.getRight()));
    }

    private Pair<ExchangeRate, LocalDateTime> getExchangeRateFromCache(Pair<String, String> currencyPair) {
        return exchangeRateCache.get(currencyPair);
    }
    private boolean isExpired(LocalDateTime fetchTime) {
        return fetchTime.plus(cacheDuration).isBefore(LocalDateTime.now());
    }
}
