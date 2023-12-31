package pl.kondziet.springbackend.domain.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Money implements Comparable<Money>{

    private String currency;
    private BigDecimal amount;

    public static Money zeroAmount(String currency) {
        return new Money(currency, BigDecimal.ZERO);
    }

    public Money add(Money other) {
        checkCurrencyMatch(other);
        BigDecimal newAmount = this.amount.add(other.amount);
        return new Money(this.currency, newAmount);
    }

    public Money subtract(Money other) {
        checkCurrencyMatch(other);
        BigDecimal newAmount = this.amount.subtract(other.amount);
        return new Money(this.currency, newAmount);
    }

    public Money applyExchangeRate(ExchangeRate exchangeRate) {
        if (!this.currency.equals(exchangeRate.baseCurrency())) {
            throw new IllegalArgumentException("Currency mismatch with ExchangeRate");
        }

        BigDecimal newAmount = this.amount.multiply(exchangeRate.rate());
        return new Money(exchangeRate.targetCurrency(), newAmount);
    }

    public Money changeSign() {
        BigDecimal newAmount = this.amount.negate();
        return new Money(this.currency, newAmount);
    }

    public boolean isLowerThanZero() {
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isGreaterThanZero() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isEqualToZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    private void checkCurrencyMatch(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies do not match");
        }
    }
    @Override
    public int compareTo(Money other) {
        checkCurrencyMatch(other);

        return amount.compareTo(other.amount);
    }
}
