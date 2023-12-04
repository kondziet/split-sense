package pl.kondziet.springbackend.adapter.out.exchangerate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kondziet.springbackend.application.domain.model.entity.ExchangeRate;
import pl.kondziet.springbackend.application.port.out.ExchangeRateFetchPort;

@Component
public class ExchangeRateAdapter implements ExchangeRateFetchPort {

    private final WebClient webClient;
    private final String apiKey;

    public ExchangeRateAdapter(@Value("${exchange-rate.api-url}") String apiUrl,
                               @Value("${exchange-rate.api-key}") String apiKey) {

        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .build();
        this.apiKey = apiKey;
    }

    @Override
    public ExchangeRate loadExchangeRate(String baseCurrency, String targetCurrency) {
        ExchangeRateResponse response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("apikey", apiKey)
                        .queryParam("base_currency", baseCurrency)
                        .queryParam("currencies", targetCurrency)
                        .build()
                )
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class).block();

        return new ExchangeRate(baseCurrency, targetCurrency, response.data().get(targetCurrency));
    }
}
