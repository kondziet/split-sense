package pl.kondziet.springbackend.infrastructure.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.kondziet.springbackend.application.service.dto.ExchangeRateResponse;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ExchangeRateFacade {

    private final RestClient restClient;
    private final String apiKey;
    private final Map<Pair<String, String>, Pair<ExchangeRate, LocalDateTime>> exchangeRateCache = new HashMap<>();
    private final CacheTimeConfig cacheTimeConfig;

    public ExchangeRateFacade(@Value("${exchange-rate.api.url}") String apiUrl,
                              @Value("${exchange-rate.api.key}") String apiKey,
                              CacheTimeConfig cacheTimeConfig) {

        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();
        this.apiKey = apiKey;
        this.cacheTimeConfig = cacheTimeConfig;
    }

    public ExchangeRate loadExchangeRate(String baseCurrency, String targetCurrency) {
        Pair<String, String> currencyPair = Pair.of(baseCurrency, targetCurrency);
        Pair<ExchangeRate, LocalDateTime> cached = getExchangeRateFromCache(currencyPair);
        if (Objects.isNull(cached) || isExpired(cached.right())) {
            ExchangeRate exchangeRate = getExchangeRateFromApi(currencyPair);
            exchangeRateCache.put(currencyPair, Pair.of(exchangeRate, cacheTimeConfig.getCurrentTime()));
            return exchangeRate;
        } else {
            return cached.left();
        }
    }

    private ExchangeRate getExchangeRateFromApi(Pair<String, String> currencyPair) {
        ExchangeRateResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("apikey", apiKey)
                        .queryParam("base_currency", currencyPair.left())
                        .queryParam("currencies", currencyPair.right())
                        .build()
                )
                .retrieve()
                .body(ExchangeRateResponse.class);

        return new ExchangeRate(currencyPair.left(), currencyPair.right(), response.data().get(currencyPair.right()));
    }

    private Pair<ExchangeRate, LocalDateTime> getExchangeRateFromCache(Pair<String, String> currencyPair) {
        return exchangeRateCache.get(currencyPair);
    }
    private boolean isExpired(LocalDateTime fetchTime) {
        return fetchTime.plus(cacheTimeConfig.getCacheDuration()).isBefore(cacheTimeConfig.getCurrentTime());
    }
}
