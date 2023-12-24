package pl.kondziet.springbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.model.entity.Debt;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.domain.model.entity.Money;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;
import pl.kondziet.springbackend.infrastructure.api.ExchangeRateFacade;

@Service
@RequiredArgsConstructor
public class ExchangeRateConverter {

    private final ExchangeRateFacade exchangeRateFacade;

    public GroupExpense convert(GroupExpense groupExpense) {

        for (Debt debt : groupExpense.getDebts()) {
            Group group = groupExpense.getGroup();
            Money moneyOwed = debt.getMoney();

            if (!moneyOwed.getCurrency().equals(group.getCurrency())) {
                ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(moneyOwed.getCurrency(), group.getCurrency());
                moneyOwed = moneyOwed.applyExchangeRate(exchangeRate);
            }

            debt.setMoney(moneyOwed);
        }


        return groupExpense;
    }
}
