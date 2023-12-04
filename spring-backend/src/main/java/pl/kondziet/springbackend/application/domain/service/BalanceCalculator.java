package pl.kondziet.springbackend.application.domain.service;

import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.*;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BalanceCalculator {

    public List<Balance> calculateGroupBalances(List<GroupExpense> groupExpenses, Group group) {

        List<UserId> groupMembers = groupExpenses.stream()
                .flatMap(expense -> expense.getDebts().stream())
                .distinct()
                .map(Debt::getDebtorId)
                .toList();

        Map<UserId, Money> balances = groupMembers.stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        val -> Money.zeroAmount(group.getCurrency())
                ));

        for (GroupExpense expense : groupExpenses) {
            UserId creditorId = expense.getCreditorId();

            Set<Debt> debts = expense.getDebts();
            for (Debt debt : debts) {

                Money creditorBalance = balances.get(expense.getCreditorId());
                Money updatedCreditorBalance = creditorBalance.add(debt.getMoney());
                balances.put(creditorId, updatedCreditorBalance);

                Money debtorBalance = balances.get(debt.getDebtorId());
                Money updatedDebtorBalance = debtorBalance.subtract(debt.getMoney());
                balances.put(debt.getDebtorId(), updatedDebtorBalance);
            }
        }

        return balances.entrySet().stream()
                .map(entry -> new Balance(entry.getKey(), entry.getValue()))
                .toList();
    }

}
