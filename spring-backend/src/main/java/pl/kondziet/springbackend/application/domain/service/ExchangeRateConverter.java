package pl.kondziet.springbackend.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.*;
import pl.kondziet.springbackend.application.port.out.ExchangeRateInputPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateConverter {

    private final ExchangeRateInputPort exchangeRateInputPort;

    public List<GroupExpense> convert (List<GroupExpense> groupExpenses, Group group) {

        for (GroupExpense expense : groupExpenses) {
            for (Debt debt : expense.getDebts()) {
                Money moneyOwed = debt.getMoney();

                if (!moneyOwed.getCurrency().equals(group.getCurrency())) {
                    ExchangeRate exchangeRate = exchangeRateInputPort.loadExchangeRate(moneyOwed.getCurrency(), group.getCurrency());
                    moneyOwed = moneyOwed.applyExchangeRate(exchangeRate);
                }

                debt.setMoney(moneyOwed);
            }
        }

        return groupExpenses;
    }
}
