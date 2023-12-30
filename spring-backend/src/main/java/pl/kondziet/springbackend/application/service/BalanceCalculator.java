package pl.kondziet.springbackend.application.service;

import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.model.entity.*;
import pl.kondziet.springbackend.domain.model.valueobjects.Balance;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BalanceCalculator {

    public List<Balance> calculateGroupBalances(List<GroupExpense> groupExpenses, Group group, List<User> groupMembers) {

        List<UUID> groupMemberIds = groupMembers.stream()
                .map(User::getId)
                .toList();

        Map<UUID, Money> balances = groupMemberIds.stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        val -> Money.zeroAmount(group.getCurrency())
                ));

        for (GroupExpense expense : groupExpenses) {
            UUID creditorId = expense.getCreditor().getId();

            Set<Debt> debts = expense.getDebts();
            for (Debt debt : debts) {

                Money creditorBalance = balances.get(expense.getCreditor().getId());
                Money updatedCreditorBalance = creditorBalance.add(debt.getMoney());
                balances.put(creditorId, updatedCreditorBalance);

                Money debtorBalance = balances.get(debt.getDebtor().getId());
                Money updatedDebtorBalance = debtorBalance.subtract(debt.getMoney());
                balances.put(debt.getDebtor().getId(), updatedDebtorBalance);
            }
        }

        return balances.entrySet().stream()
                .map(entry -> new Balance(entry.getKey(), entry.getValue()))
                .toList();
    }

}
