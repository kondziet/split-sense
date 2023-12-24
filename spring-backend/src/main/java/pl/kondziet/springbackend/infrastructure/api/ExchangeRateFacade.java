package pl.kondziet.springbackend.application.exchangerate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;

@Component
public class ExchangeRateFacade {

    private final RestClient restClient;
    private final String apiKey;

    public ExchangeRateFacade(@Value("${exchange-rate.api-url}") String apiUrl,
                              @Value("${exchange-rate.api-key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();
        this.apiKey = apiKey;
    }

    public ExchangeRate loadExchangeRate(String baseCurrency, String targetCurrency) {
        ExchangeRateResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("apikey", apiKey)
                        .queryParam("base_currency", baseCurrency)
                        .queryParam("currencies", targetCurrency)
                        .build()
                )
                .retrieve()
                .body(ExchangeRateResponse.class);

        return new ExchangeRate(baseCurrency, targetCurrency, response.data().get(targetCurrency));
    }
}
