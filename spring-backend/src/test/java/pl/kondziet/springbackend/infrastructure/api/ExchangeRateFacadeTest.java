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

    @Value("${exchange-rate.api-key}")
    private String apiKey;
    @MockBean
    private CacheTimeConfig cacheTimeConfig;
    @Autowired
    private ExchangeRateFacade exchangeRateFacade;
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8081);
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
    public void testLoadExchangeRate() {

        String baseCurrency = "USD";
        String targetCurrency = "PLN";

        stubFor(get(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "data": {
                                        "PLN": 3.8
                                    }
                                }
                                """)));

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate("USD", "PLN");

        assertThat(exchangeRate.rate()).isEqualTo(new BigDecimal("3.8"));
        assertThat(exchangeRate.baseCurrency()).isEqualTo(baseCurrency);
        assertThat(exchangeRate.targetCurrency()).isEqualTo(targetCurrency);
    }

    @Test
    public void testLoadExchangeRateFromCache() {

        String baseCurrency = "USD";
        String targetCurrency = "PLN";

        stubFor(get(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "data": {
                                        "PLN": 3.8
                                    }
                                }
                                """)));

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate("USD", "PLN");
        ExchangeRate exchangeRateFromCache = exchangeRateFacade.loadExchangeRate("USD", "PLN");

        verify(1, getRequestedFor(urlPathEqualTo("/api/v1/latest")));
        assertThat(exchangeRate).isEqualTo(exchangeRateFromCache);
    }

    @Test
    public void testLoadExchangeRateAfterCacheExpiration() {

        String baseCurrency = "USD";
        String targetCurrency = "PLN";

        when(cacheTimeConfig.getCurrentTime()).thenReturn(CacheTimeConfig.DEFAULT_CURRENT_TIME);

        stubFor(get(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "data": {
                                        "PLN": 3.8
                                    }
                                }
                                """)));

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate("USD", "PLN");

        when(cacheTimeConfig.getCurrentTime()).thenReturn(CacheTimeConfig.DEFAULT_CURRENT_TIME.plus(CacheTimeConfig.DEFAULT_CACHE_DURATION_MINUTES.plusMinutes(1)));

        stubFor(get(urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", equalTo(apiKey))
                .withQueryParam("base_currency", equalTo(baseCurrency))
                .withQueryParam("currencies", equalTo(targetCurrency))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "data": {
                                        "PLN": 3.9
                                    }
                                }
                                """)));
        ExchangeRate exchangeRateAfterCacheExpiration = exchangeRateFacade.loadExchangeRate("USD", "PLN");

        verify(2, getRequestedFor(urlPathEqualTo("/api/v1/latest")));
        assertThat(exchangeRate).isNotEqualTo(exchangeRateAfterCacheExpiration);
    }
}