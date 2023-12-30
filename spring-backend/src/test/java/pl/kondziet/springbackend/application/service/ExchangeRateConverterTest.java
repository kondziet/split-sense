package pl.kondziet.springbackend.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kondziet.springbackend.domain.model.entity.Debt;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.domain.model.entity.Money;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;
import pl.kondziet.springbackend.infrastructure.api.ExchangeRateFacade;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateConverterTest {

    @Mock
    ExchangeRateFacade exchangeRateFacade;
    @InjectMocks
    ExchangeRateConverter exchangeRateConverter;

    @Test
    void convertWithSameExchangeRates() {
        GroupExpense groupExpense = GroupExpense.builder()
                .group(Group.builder()
                        .currency("PLN")
                        .build())
                .debts(Set.of(
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("12.25"))
                                        .currency("USD")
                                        .build())
                                .build(),
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("4.65"))
                                        .currency("USD")
                                        .build())
                                .build()
                ))
                .build();
        when(exchangeRateFacade.loadExchangeRate("USD", "PLN"))
                .thenReturn(new ExchangeRate("USD", "PLN", new BigDecimal("0.25")));

        GroupExpense convertedExpense = exchangeRateConverter.convert(groupExpense);

        assertThat(convertedExpense.getDebts())
                .hasSameElementsAs(Set.of(
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("3.0625"))
                                        .currency("PLN")
                                        .build())
                                .build(),
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("1.1625"))
                                        .currency("PLN")
                                        .build())
                                .build()
                ));
    }

    @Test
    void convertWithDifferentExchangeRates() {
        GroupExpense groupExpense = GroupExpense.builder()
                .group(Group.builder()
                        .currency("PLN")
                        .build())
                .debts(Set.of(
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("7.33"))
                                        .currency("EUR")
                                        .build())
                                .build(),
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("18.91"))
                                        .currency("GBP")
                                        .build())
                                .build()
                ))
                .build();

        when(exchangeRateFacade.loadExchangeRate("EUR", "PLN"))
                .thenReturn(new ExchangeRate("EUR", "PLN", new BigDecimal("4.22")));

        when(exchangeRateFacade.loadExchangeRate("GBP", "PLN"))
                .thenReturn(new ExchangeRate("GBP", "PLN", new BigDecimal("5.1")));

        GroupExpense convertedExpense = exchangeRateConverter.convert(groupExpense);

        assertThat(convertedExpense.getDebts())
                .contains(
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("30.9326"))
                                        .currency("PLN")
                                        .build())
                                .build(),
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("96.441"))
                                        .currency("PLN")
                                        .build())
                                .build()
                );
    }


    @Test
    void shouldNotInvokeApiForSameCurrencyConversion() {
        GroupExpense groupExpense = GroupExpense.builder()
                .group(Group.builder()
                        .currency("PLN")
                        .build())
                .debts(Set.of(
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("12.25"))
                                        .currency("PLN")
                                        .build())
                                .build()
                ))
                .build();
        lenient().when(exchangeRateFacade.loadExchangeRate("PLN", "PLN"))
                .thenReturn(new ExchangeRate("PLN", "PLN", new BigDecimal("1")));

        GroupExpense convertedExpense = exchangeRateConverter.convert(groupExpense);

        assertThat(convertedExpense.getDebts())
                .hasSameElementsAs(Set.of(
                        Debt.builder()
                                .money(Money.builder()
                                        .amount(new BigDecimal("12.25"))
                                        .currency("PLN")
                                        .build())
                                .build()
                ));
        verify(exchangeRateFacade, never()).loadExchangeRate("PLN", "PLN");
    }
}

