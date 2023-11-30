package pl.kondziet.springbackend.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.*;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.in.CalculateGroupReimbursementsUseCase;
import pl.kondziet.springbackend.application.port.out.ExchangeRateInputPort;
import pl.kondziet.springbackend.application.port.out.ExpenseInputPort;
import pl.kondziet.springbackend.application.port.out.GroupInputPort;
import pl.kondziet.springbackend.application.port.out.GroupMembershipInputPort;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CalculateGroupReimbursementsService implements CalculateGroupReimbursementsUseCase {

    private final ExpenseInputPort expenseInputPort;
    private final GroupInputPort groupInputPort;
    private final GroupMembershipInputPort groupMembershipInputPort;
    private final ExchangeRateInputPort exchangeRateInputPort;

    @Override
    public List<Reimbursement> calculateReimbursements(GroupId groupId) {
        Group group = groupInputPort.loadGroupById(groupId);
        List<GroupExpense> groupExpenses = expenseInputPort.loadGroupExpenses(groupId);

        Map<UserId, Money> balances = calculateUserBalances(group, groupExpenses);

        List<Reimbursement> reimbursements = new ArrayList<>();

        while (!balances.isEmpty()) {
            Map.Entry<UserId, Money> highestBalance = findBalanceWithHighestAmount(balances);
            Map.Entry<UserId, Money> lowestBalance = findBalanceWithLowestAmount(balances);

            Money reimbursementAmount = Money.zeroAmount(group.getCurrency());
            Money highestLowestDifference = highestBalance.getValue().add(lowestBalance.getValue());

            if (highestLowestDifference.isEqualToZero()) {
                reimbursementAmount = lowestBalance.getValue().changeSign();

                balances.remove(highestBalance.getKey());
                balances.remove(lowestBalance.getKey());
            } else if (highestLowestDifference.isLowerThanZero()) {
                reimbursementAmount = lowestBalance.getValue().subtract(highestLowestDifference).changeSign();

                balances.remove(highestBalance.getKey());
                balances.remove(lowestBalance.getKey());

                balances.put(lowestBalance.getKey(), highestLowestDifference);
            } else if (highestLowestDifference.isGreaterThanZero()) {
                reimbursementAmount = lowestBalance.getValue().changeSign();

                balances.remove(highestBalance.getKey());
                balances.remove(lowestBalance.getKey());

                balances.put(highestBalance.getKey(), highestLowestDifference);
            }

            reimbursements.add(new Reimbursement(
                    lowestBalance.getKey(),
                    highestBalance.getKey(),
                    reimbursementAmount)
            );
        }

        return reimbursements;
    }

    private Map.Entry<UserId, Money> findBalanceWithHighestAmount(Map<UserId, Money> balances) {
        return balances.entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getValue().getAmount()))
                .orElse(null);
    }

    private Map.Entry<UserId, Money> findBalanceWithLowestAmount(Map<UserId, Money> balances) {
        return balances.entrySet().stream()
                .min(Comparator.comparing(entry -> entry.getValue().getAmount()))
                .orElse(null);
    }

    private Map<UserId, Money> calculateUserBalances(Group group, List<GroupExpense> expenses) {
        String groupCurrency = group.getCurrency();
        List<User> groupMembers = groupMembershipInputPort.loadGroupMembers(group.getId());

        Map<UserId, Money> balances = groupMembers.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        val -> Money.zeroAmount(groupCurrency)
                ));

        for (GroupExpense expense : expenses) {
            UserId creditorId = expense.getCreditorId();

            Set<Debt> debts = expense.getDebts();
            for (Debt debt : debts) {
                Money moneyOwed = debt.getMoney();

                if (!moneyOwed.getCurrency().equals(groupCurrency)) {
                    ExchangeRate exchangeRate = exchangeRateInputPort.loadExchangeRate(moneyOwed.getCurrency(), groupCurrency);
                    moneyOwed = moneyOwed.applyExchangeRate(exchangeRate);
                }

                Money creditorBalance = balances.get(expense.getCreditorId());
                Money updatedCreditorBalance = creditorBalance.add(moneyOwed);
                balances.put(creditorId, updatedCreditorBalance);

                Money debtorBalance = balances.get(debt.getDebtorId());
                Money updatedDebtorBalance = debtorBalance.subtract(moneyOwed);
                balances.put(debt.getDebtorId(), updatedDebtorBalance);
            }
        }

        return balances;
    }

}
