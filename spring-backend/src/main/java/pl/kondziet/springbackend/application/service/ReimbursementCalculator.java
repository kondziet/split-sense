package pl.kondziet.springbackend.application.service;

import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.Money;
import pl.kondziet.springbackend.domain.model.valueobjects.Balance;
import pl.kondziet.springbackend.domain.model.valueobjects.Reimbursement;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReimbursementCalculator {

    public List<Reimbursement> calculateGroupReimbursements(List<Balance> groupBalances, Group group) {

        SortedContainer<Balance> balances = SortedContainer.of(groupBalances);

        List<Reimbursement> reimbursements = new ArrayList<>();

        if (balances.size() <= 1) {
            return reimbursements;
        }

        while (!balances.isEmpty()) {
            Balance maxBalance = balances.getMax();
            Balance minBalance = balances.getMin();

            Money amount = Money.zeroAmount(group.getCurrency());
            Money balanceDifference = maxBalance.getMoney().add(minBalance.getMoney());

            if (balanceDifference.isEqualToZero()) {
                amount = minBalance.getMoney().changeSign();

                balances.removeMax();
                balances.removeMin();
            } else if (balanceDifference.isLowerThanZero()) {
                amount = minBalance.getMoney().subtract(balanceDifference).changeSign();

                balances.removeMax();

                minBalance.setMoney(balanceDifference);
                balances.updateMin(minBalance);
            } else if (balanceDifference.isGreaterThanZero()) {
                amount = minBalance.getMoney().changeSign();

                balances.removeMin();

                maxBalance.setMoney(balanceDifference);
                balances.updateMax(maxBalance);
            }

            reimbursements.add(new Reimbursement(
                    minBalance.getUserId(),
                    maxBalance.getUserId(),
                    amount)
            );
        }

        return reimbursements;
    }
}
