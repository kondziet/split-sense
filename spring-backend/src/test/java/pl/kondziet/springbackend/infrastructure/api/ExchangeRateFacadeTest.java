package pl.kondziet.springbackend.infrastructure.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeRateFacadeTest {

    @Value("${wiremock.api.key}")
    private String apiKey;
    @Value("${exchange-rate.cache-expiration.minutes}")
    private Duration cacheExpirationDuration;
    @MockBean
    private Clock clock;
    @Autowired
    private ExchangeRateFacade exchangeRateFacade;
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll(@Value("${wiremock.api.host}") String apiHost,
                          @Value("${wiremock.api.port}") int apiPort) {
        wireMockServer = new WireMockServer(apiPort);
        wireMockServer.start();
        WireMock.configureFor(apiHost, apiPort);
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp() {
        when(clock.instant()).thenReturn(Instant.parse("2021-01-01T00:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));
    }

    @Test
    void testLoadExchangeRate() {
        String baseCurrency = "USD";
        String targetCurrency = "PLN";
        String rate = String.valueOf(3.8);
        stubExchangeRateRequest(baseCurrency, targetCurrency, rate);

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        verifyExchangeRateResponse(1, baseCurrency, targetCurrency);

        assertThat(exchangeRate.rate()).isEqualTo(new BigDecimal(rate));
        assertThat(exchangeRate.baseCurrency()).isEqualTo(baseCurrency);
        assertThat(exchangeRate.targetCurrency()).isEqualTo(targetCurrency);
    }

    @Test
    void testLoadExchangeRateFromCache() {
        String baseCurrency = "USD";
        String targetCurrency = "PLN";
        String rate = String.valueOf(3.8);

        stubExchangeRateRequest(baseCurrency, targetCurrency, rate);

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);
        ExchangeRate exchangeRateFromCache = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        verifyExchangeRateResponse(1, baseCurrency, targetCurrency);
        assertThat(exchangeRate).isEqualTo(exchangeRateFromCache);
    }

    @Test
    void testLoadExchangeRateAfterCacheExpiration() {
        String baseCurrency = "USD";
        String targetCurrency = "PLN";
        String rate = String.valueOf(3.8);
        String rateAfterCacheExpiration = String.valueOf(3.9);

        stubExchangeRateRequest(baseCurrency, targetCurrency, rate);

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        when(clock.instant()).thenReturn(
                Instant.parse("2021-01-01T02:00:00Z")
                        .plus(cacheExpirationDuration)
                        .plus(Duration.ofMinutes(1))
        );
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        stubExchangeRateRequest(baseCurrency, targetCurrency, rateAfterCacheExpiration);

        ExchangeRate exchangeRateAfterCacheExpiration = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        verifyExchangeRateResponse(2, baseCurrency, targetCurrency);
        assertThat(exchangeRate).isNotEqualTo(exchangeRateAfterCacheExpiration);
    }

    private void stubExchangeRateRequest(String baseCurrency, String targetCurrency, String rate) {
        stubFor(get(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(String.format("{\"data\": {\"%s\": %s}}", targetCurrency, rate))));
    }

    private void verifyExchangeRateResponse(int expectedRequestCount, String baseCurrency, String targetCurrency) {
        verify(expectedRequestCount, getRequestedFor(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency)));
    }
}