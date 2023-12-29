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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeRateFacadeTest {

    @Value("${wiremock.api.key}")
    private String apiKey;
    @MockBean
    private CacheTimeConfig cacheTimeConfig;
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
        when(cacheTimeConfig.getCurrentTime()).thenReturn(CacheTimeConfig.DEFAULT_CURRENT_TIME);
        when(cacheTimeConfig.getCacheDuration()).thenReturn(CacheTimeConfig.DEFAULT_CACHE_DURATION_MINUTES);
    }

    @Test
    void testLoadExchangeRate() {
        String baseCurrency = "USD";
        String targetCurrency = "PLN";
        stubExchangeRateRequest(baseCurrency, targetCurrency, 3.8);

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        verifyExchangeRateResponse(1, baseCurrency, targetCurrency, exchangeRate, new BigDecimal("3.8"));
    }

    @Test
    void testLoadExchangeRateFromCache() {
        String baseCurrency = "USD";
        String targetCurrency = "PLN";
        stubExchangeRateRequest(baseCurrency, targetCurrency, 3.8);

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);
        ExchangeRate exchangeRateFromCache = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        verifyExchangeRateResponse(1, baseCurrency, targetCurrency, exchangeRate, new BigDecimal("3.8"));
        assertThat(exchangeRate).isEqualTo(exchangeRateFromCache);
    }

    @Test
    void testLoadExchangeRateAfterCacheExpiration() {
        String baseCurrency = "USD";
        String targetCurrency = "PLN";

        stubExchangeRateRequest(baseCurrency, targetCurrency, 3.8);
        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        when(cacheTimeConfig.getCurrentTime())
                .thenReturn(CacheTimeConfig.DEFAULT_CURRENT_TIME.plus(CacheTimeConfig.DEFAULT_CACHE_DURATION_MINUTES.plusMinutes(1)));

        stubExchangeRateRequest(baseCurrency, targetCurrency, 3.9);
        ExchangeRate exchangeRateAfterCacheExpiration = exchangeRateFacade.loadExchangeRate(baseCurrency, targetCurrency);

        verifyExchangeRateResponse(2, baseCurrency, targetCurrency, exchangeRateAfterCacheExpiration, new BigDecimal("3.9"));
        assertThat(exchangeRate).isNotEqualTo(exchangeRateAfterCacheExpiration);
    }

    private void stubExchangeRateRequest(String baseCurrency, String targetCurrency, double rate) {
        stubFor(get(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(String.format("{\"data\": {\"%s\": %s}}", targetCurrency, rate))));
    }

    private void verifyExchangeRateResponse(int expectedRequestCount, String baseCurrency, String targetCurrency,
                                            ExchangeRate exchangeRate, BigDecimal expectedRate) {
        verify(expectedRequestCount, getRequestedFor(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency)));

        assertThat(exchangeRate.rate()).isEqualTo(expectedRate);
        assertThat(exchangeRate.baseCurrency()).isEqualTo(baseCurrency);
        assertThat(exchangeRate.targetCurrency()).isEqualTo(targetCurrency);
    }
}