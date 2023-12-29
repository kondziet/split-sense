package pl.kondziet.springbackend.infrastructure.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeRateFacadeTest {

    @Autowired
    private ExchangeRateFacade exchangeRateFacade;

    @Test
    public void testLoadExchangeRate() {
        WireMockServer wireMockServer = new WireMockServer(8081); // Start a WireMock server on port 8081
        wireMockServer.start();

        WireMock.configureFor("localhost", 8081); // Configure the WireMock client to interact with the server

        String apiKey = "123";
        String baseCurrency = "USD";
        String targetCurrency = "PLN";

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/v1/latest"))
                .withQueryParam("apikey", WireMock.equalTo(apiKey))
                .withQueryParam("base_currency", WireMock.equalTo(baseCurrency))
                .withQueryParam("currencies", WireMock.equalTo(targetCurrency))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"data\": {\"" + targetCurrency + "\": \"3.88\"}}")));

        ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate("USD", "PLN");

        wireMockServer.stop();

        System.out.println(exchangeRate);

    }
}