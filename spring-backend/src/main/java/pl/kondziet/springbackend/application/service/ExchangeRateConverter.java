package pl.kondziet.springbackend.application.exchangerate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.model.entity.Debt;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.domain.model.entity.Money;
import pl.kondziet.springbackend.domain.model.valueobjects.ExchangeRate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateConverter {

    private final ExchangeRateFacade exchangeRateFacade;

    public List<GroupExpense> convert (List<GroupExpense> groupExpenses, Group group) {

        for (GroupExpense expense : groupExpenses) {
            for (Debt debt : expense.getDebts()) {
                Money moneyOwed = debt.getMoney();

                if (!moneyOwed.getCurrency().equals(group.getCurrency())) {
                    ExchangeRate exchangeRate = exchangeRateFacade.loadExchangeRate(moneyOwed.getCurrency(), group.getCurrency());
                    moneyOwed = moneyOwed.applyExchangeRate(exchangeRate);
                }

                debt.setMoney(moneyOwed);
            }
        }

        return groupExpenses;
    }
}
